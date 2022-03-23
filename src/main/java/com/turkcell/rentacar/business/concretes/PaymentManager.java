package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceForPaymentRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentForPaymentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                          @Lazy RentService rentService, @Lazy InvoiceService invoiceService,
                          @Lazy AdditionalServiceService additionalServiceService,@Lazy IndividualCustomerService individualCustomerService,
                          PosService posService) {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.rentService = rentService;
        this.invoiceService = invoiceService;
        this.additionalServiceService = additionalServiceService;
        this.individualCustomerService = individualCustomerService;
        this.posService = posService;

    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {

        if (posService.makePayment(createPaymentRequest.getCardNumber(),createPaymentRequest.getCardOwner(),"1",createPaymentRequest.getCardExpiryDate())){
            System.out.println("Ödeme başarılı.");
        }

        runPaymentSuccessor(createPaymentRequest);


        return new SuccessResult("Ödeme başarılı.");
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

        //createOrderedAdditionalServiceRequest = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);

        CreateRentForPaymentRequest createRentForPaymentRequest = createPaymentRequest.getCreateRentForPaymentRequest();
        Rent rent = this.modelMapperService.forRequest().map(createRentForPaymentRequest,Rent.class);
        rent.setOrderedAdditionalServices(orderedAdditionalService);
        //rent.setCar(createRentForPaymentRequest.ge);
        rent.setCustomer(this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(createRentForPaymentRequest.getCustomer()).getData());
        rent.setRentId(0);
        rent.setStartDate(createRentForPaymentRequest.getStartDate());
        rent.setFinishDate(createRentForPaymentRequest.getFinishDate());
        this.rentService.save(rent);

        //this.rentService.addRentForIndividualCustomer(createPaymentRequest.getCreateRentRequest());

        List<CreateInvoiceForPaymentRequest> createInvoiceForPaymentRequestList = createPaymentRequest.getCreateInvoiceForPaymentRequests();
        List<Invoice> invoices = new ArrayList<Invoice>(createInvoiceForPaymentRequestList.size());

        //this.modelMapperService.forRequest().map(createInvoiceRequestList,Invoice.class);
        double price = 0;
        for (int i=0;i<createInvoiceForPaymentRequestList.size();i++) {
            Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceForPaymentRequestList.get(i),Invoice.class);
            invoice.setRent(rent);
            this.invoiceService.save(invoice);
            invoices.add(invoice);
            price += invoice.getFinalPrice();
        }

        createPaymentRequest.setTotalPrice(price);

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest,Payment.class);
        payment.setOrderedAdditionalService(orderedAdditionalService);
        payment.setRent(rent);
        payment.setInvoices(invoices);

        this.paymentDao.save(payment);
    }
}
