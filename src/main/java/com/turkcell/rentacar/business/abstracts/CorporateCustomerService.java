package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.corporateCustomerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

    DataResult<CorporateCustomerByIdDto> getCorporateCustomerByIdDtoByUserId(int userId);

    DataResult<CorporateCustomer> getCorporateCustomerByUserId(int userId);
}
