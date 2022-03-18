package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rent;

import java.util.List;

public interface RentService {

    DataResult<List<RentListDto>> getAll();

    Result addRentForIndividualCustomer(CreateRentRequest createRentRequest) throws BusinessException;

    Result addRentForCorporateCustomer(CreateRentRequest createRentRequest) throws BusinessException;

    DataResult<RentByIdDto> getRentByIdDtoByRentId(int id) throws BusinessException;

    Result updateRentForIndividualCustomer(UpdateRentRequest updateRentRequest) throws BusinessException;

    Result updateRentForCorporateCustomer(UpdateRentRequest updateRentRequest) throws BusinessException;

    Result deleteByRentId(int rentId) throws BusinessException;

    DataResult <List<RentListDto>> getAllRentsByCarId(int carId);

    DataResult<Rent> getRentByRentId(int id);
}