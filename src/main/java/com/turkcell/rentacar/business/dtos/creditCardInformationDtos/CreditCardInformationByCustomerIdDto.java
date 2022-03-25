package com.turkcell.rentacar.business.dtos.creditCardInformationDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardInformationByCustomerIdDto {

    private int id;

    private String cardOwner;

    private String cardNumber;

    private LocalDate cardExpiryDate;
}
