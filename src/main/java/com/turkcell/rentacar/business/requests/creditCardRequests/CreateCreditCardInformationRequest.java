package com.turkcell.rentacar.business.requests.creditCardRequests;

import com.turkcell.rentacar.entities.concretes.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardInformationRequest {

    private String cardOwner;

    private String cardNumber;

    private String cvc;

    private LocalDate cardExpiryDate;

    private Integer customer;
}
