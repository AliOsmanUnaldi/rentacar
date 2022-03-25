package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByCustomerIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface PaymentService {

    Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;

    DataResult<List<PaymentListDto>> getAllPayments();

    DataResult<PaymentByIdDto> getPaymentDtosByPaymentId(int id);

    DataResult<List<PaymentByCustomerIdDto>> getPaymentDtoSByCustomerId(int customerId);

}
