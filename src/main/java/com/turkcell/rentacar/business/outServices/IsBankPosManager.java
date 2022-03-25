package com.turkcell.rentacar.business.outServices;

import com.turkcell.rentacar.business.abstracts.PosService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Primary
@Service
public class IsBankPosManager implements PosService {

    public boolean makePayment(String cardNumber, String cardOwner, String cvv, LocalDate expireDate){
        return true;
    }
}
