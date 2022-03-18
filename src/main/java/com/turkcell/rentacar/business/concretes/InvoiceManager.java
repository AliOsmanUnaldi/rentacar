package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final ModelMapperService modelMapperService;
    private final RentService rentService;
    private final IndividualCustomerService individualCustomerService;

    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, @Lazy RentService rentService,
                          @Lazy IndividualCustomerService individualCustomerService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
        this.individualCustomerService = individualCustomerService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> result = this.invoiceDao.findAll();

        List<InvoiceListDto> response = result.stream()
                .map(invoice -> this.modelMapperService.forDto()
                        .map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response,"All invoices are listed.");
    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        
        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest,Invoice.class);
        invoice.setCustomer(this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(createInvoiceRequest.getCustomer()).getData());
        invoice.setRent(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData());

        invoice.setFinalPrice(calculatePriceForRentedDays(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData())
                +calculateThePriceIfCarDeliveredToDiffrentCity(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData())
                +calculatePriceIfAdditionalServicesAreDemanded(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData()));
        invoice.setCreationDate(LocalDate.now());

        invoice.setStartDate(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData().getStartDate());

        invoice.setFinishDate(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData().getFinishDate());

        this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData();

        invoice.setNumberOfRentedDays(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData().getFinishDate()
                .compareTo(this.rentService.getRentByRentId(createInvoiceRequest.getRent()).getData().getStartDate()));

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice is added successfully.");
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

        Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest,Invoice.class);
        invoice.setFinalPrice(calculatePriceForRentedDays(invoice.getRent())
                +calculateThePriceIfCarDeliveredToDiffrentCity(invoice.getRent())
                +calculatePriceIfAdditionalServicesAreDemanded(invoice.getRent()));

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice is updated successfully.");
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

        this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceId());

        return new SuccessResult("Invoice is deleted successfully.");
    }

    @Override
    public DataResult<InvoiceByIdDto> getInvoiceDtoByInvoiceId(int invoiceId) {

        Invoice invoice = this.invoiceDao.getById(invoiceId);
        InvoiceByIdDto response = this.modelMapperService.forDto().map(invoice, InvoiceByIdDto.class);

        return new SuccessDataResult<InvoiceByIdDto>(response,"Invoice is found by id: "+invoiceId+".");
    }

    private double calculatePriceForRentedDays(Rent rent){

        return rent.getFinishDate().compareTo(rent.getStartDate())*rent.getCar().getDailyPrice();
    }

    private double calculateThePriceIfCarDeliveredToDiffrentCity(Rent rent){

        return rent.getRentedCity() != rent.getDeliveredCity() ? 750 : 0;
    }

    private double calculatePriceIfAdditionalServicesAreDemanded(Rent rent){

        double additionalServicesDailyPrice = 0;

        for (AdditionalService additionalService : rent.getOrderedAdditionalServices().getAdditionalServices()
                ) {
            additionalServicesDailyPrice += additionalService.getAdditionalServiceDailyPrice();
        }

        return additionalServicesDailyPrice*rent.getFinishDate().compareTo(rent.getStartDate());
    }
}
