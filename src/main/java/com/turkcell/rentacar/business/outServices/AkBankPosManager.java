package com.turkcell.rentacar.business.outServices;

import com.turkcell.rentacar.business.abstracts.PosService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class AkBankPosManager implements PosService {

    public boolean makePayment(String cardOwner,String cardNumber, String cvc, LocalDate expireDate){
        return false;
    }

}
