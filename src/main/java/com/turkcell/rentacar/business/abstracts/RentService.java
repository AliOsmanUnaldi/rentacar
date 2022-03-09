package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface RentService {
    DataResult<List<RentListDto>> getAll();

    Result add(CreateRentRequest createRentRequest) throws BusinessException;

    DataResult<RentByIdDto> getByRentId(int id) throws BusinessException;

    Result update(UpdateRentRequest updateRentRequest) throws BusinessException;

    Result deleteByRentId(int rentId) throws BusinessException;

    DataResult <List<RentListDto>> getAllRentsByCarId(int carId);
}