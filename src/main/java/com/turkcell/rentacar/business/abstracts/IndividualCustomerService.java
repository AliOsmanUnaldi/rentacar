package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);

    DataResult<IndividualCustomerByIdDto> getIndividualCustomerByIdDtoByUserId(int userId);

    DataResult<IndividualCustomer> getIndividualCustomerByUserId(int userId);
}
