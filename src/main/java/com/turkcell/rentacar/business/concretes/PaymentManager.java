package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByCustomerIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceForPaymentRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentForPaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private PaymentDao paymentDao;
    private ModelMapperService modelMapperService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;
    private RentService rentService;
    private InvoiceService invoiceService;
    private AdditionalServiceService additionalServiceService;
    private IndividualCustomerService individualCustomerService;
    private PosService posService;
    private CityService cityService;

    @Autowired
    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                          @Lazy RentService rentService, @Lazy InvoiceService invoiceService,
                          @Lazy AdditionalServiceService additionalServiceService,@Lazy IndividualCustomerService individualCustomerService,
                          PosService posService, CityService cityService) {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.rentService = rentService;
        this.invoiceService = invoiceService;
        this.additionalServiceService = additionalServiceService;
        this.individualCustomerService = individualCustomerService;
        this.posService = posService;
        this.cityService = cityService;

    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {

        if (!posService.makePayment(createPaymentRequest.getCardNumber(),createPaymentRequest.getCardOwner(), createPaymentRequest.getCvc(), createPaymentRequest.getCardExpiryDate())){
            throw new BusinessException(BusinessMessages.PaymentMessages.PAYMENT_FAILED);
        }

        runPaymentSuccessor(createPaymentRequest);

        return new SuccessResult(BusinessMessages.PaymentMessages.PAYMENT_SUCCESSFUL);
    }

    @Override
    public DataResult getAllPayments() {

        List<Payment> result = this.paymentDao.findAll();
        List<PaymentListDto> response = result.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment,PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response,BusinessMessages.PaymentMessages.PAYMENTS_LISTED);
    }

    @Override
    public DataResult<PaymentByIdDto> getPaymentDtosByPaymentId(int id) {

        Payment payment = this.paymentDao.getById(id);
        PaymentByIdDto response = this.modelMapperService.forDto().map(payment,PaymentByIdDto.class);

        return new SuccessDataResult<PaymentByIdDto>(response,BusinessMessages.PaymentMessages.PAYMENT_FOUND);
    }

    @Override
    public DataResult<List<PaymentByCustomerIdDto>> getPaymentDtoSByCustomerId(int customerId) {

        List<Payment> result = this.paymentDao.getAllByRent_Customer_UserId(customerId);
        List<PaymentByCustomerIdDto> response = result.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment,PaymentByCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentByCustomerIdDto>>(response,BusinessMessages.PaymentMessages.PAYMENT_FOUND_BY_CUSOMER);
    }

    @Transactional
    public  void runPaymentSuccessor(CreatePaymentRequest createPaymentRequest){

        CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = createPaymentRequest
                .getCreateOrderedAdditionalServiceRequest();

        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
                .map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);

        Set<AdditionalService> a = new HashSet<>();
        for (Integer b:createOrderedAdditionalServiceRequest.getAdditionalServices()
        ) {
            a.add(this.additionalServiceService.getAdditionalServiceByAdditionalService(b));
        }
        orderedAdditionalService.setAdditionalServices(a);

        this.orderedAdditionalServiceService.save(orderedAdditionalService);

        CreateRentForPaymentRequest createRentForPaymentRequest = createPaymentRequest.getCreateRentForPaymentRequest();
        Rent rent = this.modelMapperService.forRequest().map(createRentForPaymentRequest,Rent.class);
        rent.setOrderedAdditionalServices(orderedAdditionalService);
        rent.setCustomer(this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(createRentForPaymentRequest.getCustomer()).getData());
        rent.setRentId(0);
        rent.setStartDate(createRentForPaymentRequest.getStartDate());
        rent.setFinishDate(createRentForPaymentRequest.getFinishDate());
        rent.setRentedCity(this.cityService.getCityByIdWithoutDto(createRentForPaymentRequest.getRentedCity()));
        rent.setDeliveredCity(this.cityService.getCityByIdWithoutDto(createRentForPaymentRequest.getDeliveredCity()));

        this.rentService.save(rent);

        List<CreateInvoiceForPaymentRequest> createInvoiceForPaymentRequestList = createPaymentRequest.getCreateInvoiceForPaymentRequests();
        List<Invoice> invoices = new ArrayList<Invoice>(createInvoiceForPaymentRequestList.size());

        double price = 0;
        for (int i=0;i<createInvoiceForPaymentRequestList.size();i++) {
            Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceForPaymentRequestList.get(i),Invoice.class);
            invoice.setRent(rent);
            invoice.setCustomer(this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(rent.getCustomer().getUserId()).getData());
            this.invoiceService.save(invoice);
            invoices.add(invoice);
            price += invoice.getFinalPrice();
        }

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest,Payment.class);
        payment.setTotalPrice(price);
        payment.setOrderedAdditionalService(orderedAdditionalService);
        payment.setRent(rent);
        payment.setInvoices(invoices);

        this.paymentDao.save(payment);
    }
}
