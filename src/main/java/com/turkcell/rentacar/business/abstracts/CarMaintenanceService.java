package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CarMaintenanceService {

    DataResult<List<CarMaintenanceListDto>> getAll();

    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

    DataResult<CarMaintenanceByIdDto> getById(int id) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

    DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) ;
}