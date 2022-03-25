package com.turkcell.rentacar.business.adapters.posAdapters;

import com.turkcell.rentacar.business.outServices.AkBankPosManager;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AkBankPosAdapter {

    public Result pos(String cardOwner, String cardNumber, String cvc, LocalDate expireDate){

        AkBankPosManager akBankPosManager = new AkBankPosManager();
        boolean posResult = akBankPosManager.makePayment(cardNumber,cardOwner,cvc,expireDate);

        if(posResult){

            return new SuccessResult();

        }

        return new ErrorResult();
    }
}
