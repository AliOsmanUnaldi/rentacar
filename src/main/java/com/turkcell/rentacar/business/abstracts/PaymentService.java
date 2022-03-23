package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface PaymentService {

    Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;
}
