package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface AdditionalServiceService {
    DataResult<List<AdditionalServiceListDto>> getAll();

    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;

    DataResult<AdditionalServiceDto> getById(int id) throws BusinessException;

    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;

    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;


}
