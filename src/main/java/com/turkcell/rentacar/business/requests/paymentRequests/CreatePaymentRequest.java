package com.turkcell.rentacar.business.requests.paymentRequests;

import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceForPaymentRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentForPaymentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

    private LocalDate paymentDate;

    private String cardOwner;

    private String cardNumber;

    private String cvc;

    private LocalDate cardExpiryDate;

    private CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest;

    private CreateRentForPaymentRequest createRentForPaymentRequest;

    private List<CreateInvoiceForPaymentRequest> createInvoiceForPaymentRequests;
}
