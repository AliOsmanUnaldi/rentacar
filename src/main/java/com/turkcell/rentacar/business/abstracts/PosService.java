package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;

public interface PosService {

    boolean makePayment(String cardNumber, String cardOwner, String cvv, LocalDate expireDate);
}
