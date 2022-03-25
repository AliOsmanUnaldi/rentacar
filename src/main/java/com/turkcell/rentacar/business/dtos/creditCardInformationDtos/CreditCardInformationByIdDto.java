package com.turkcell.rentacar.business.dtos.creditCardInformationDtos;

import com.turkcell.rentacar.entities.concretes.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardInformationByIdDto {

    private int id;

    private String cardOwner;

    private String cardNumber;

    private LocalDate cardExpiryDate;

    private int customer_customerId;
}
