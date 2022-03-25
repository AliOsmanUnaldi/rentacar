package com.turkcell.rentacar.business.adapters.posAdapters;

import com.turkcell.rentacar.business.outServices.IsBankPosManager;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Primary
public class IsBankPosAdapter {

    public Result pos(String cardNumber, String cardOwner, String cvc, LocalDate expireDate){

        IsBankPosManager isBankPosManager = new IsBankPosManager();
        boolean posResult = isBankPosManager.makePayment(cardNumber,cardOwner,cvc,expireDate);

        if(posResult){

        return new SuccessResult();

        }

        return new ErrorResult();
    }



}
