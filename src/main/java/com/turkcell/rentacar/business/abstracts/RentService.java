package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForIndividualCustomer;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rent;

import java.util.List;

public interface RentService {

    DataResult<List<RentListDto>> getAll();

    Result addRentForIndividualCustomer(CreateRentRequestForIndividualCustomer createRentRequestForIndividualCustomer) throws BusinessException;

    Result addRentForCorporateCustomer(CreateRentRequestForCorporateCustomer createRentRequestForCorporateCustomer) throws BusinessException;

    DataResult<RentByIdDto> getRentByIdDtoByRentId(int id) throws BusinessException;

    Result updateRentForIndividualCustomer(UpdateRentRequestForIndividualCustomer updateRentRequestForIndividualCustomer) throws BusinessException;

    Result updateRentForCorporateCustomer(UpdateRentRequestForCorporateCustomer updateRentRequestForCorporateCustomer) throws BusinessException;

    Result deleteByRentId(int rentId) throws BusinessException;

    DataResult <List<RentListDto>> getAllRentsByCarId(int carId);

    DataResult<Rent> getRentByRentId(int id);
}