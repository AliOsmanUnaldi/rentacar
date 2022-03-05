package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface RentService {
    DataResult<List<RentListDto>> getAll();

    Result add(CreateRentRequest createRentRequest) throws BusinessException;

    DataResult<RentByIdDto> getById(int id);

    Result update(UpdateRentRequest updateRentRequest) throws BusinessException;

    Result deleteByRentId(int rentId);
}
