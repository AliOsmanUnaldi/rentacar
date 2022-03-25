package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByCustomerIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationListDto;
import com.turkcell.rentacar.business.requests.creditCardRequests.CreateCreditCardInformationRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CreditCardInformationService {

    Result add(CreateCreditCardInformationRequest createCreditCardInformationRequest) throws BusinessException;

    DataResult<List<CreditCardInformationListDto>> getAll();

    DataResult<CreditCardInformationByIdDto> getCreditCardInformationDtoById(int id);

    DataResult<List<CreditCardInformationByCustomerIdDto>> getCreditCardInformationDtoByCustomerId(int customerId) throws BusinessException;
}
