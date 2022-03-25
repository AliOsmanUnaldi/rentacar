package com.turkcell.rentacar.business.dtos.paymentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {

    private int id;

    private LocalDate paymentDate;

    private String cardOwner;

    private String cardNumber;

    private String cvc;

    private LocalDate cardExpiryDate;

    private Integer orderedAdditionalService;

    private Integer rent;

    private double totalPrice;
}
