package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordListDto;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.CreateCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.DeleteCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.UpdateCarDamageRecordRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CarDamageRecordService {

    DataResult<List<CarDamageRecordListDto>> getAll();

    Result add(CreateCarDamageRecordRequest carDamageRecordRequest) throws BusinessException;

    Result update(UpdateCarDamageRecordRequest updateCarDamageRecordRequest) throws BusinessException;

    Result delete(DeleteCarDamageRecordRequest deleteCarDamageRecordRequest) throws BusinessException;

    DataResult<CarDamageRecordByIdDto> getCarDamageRecordByIdDtoByCarDamageRecordId(int id) throws BusinessException;

    DataResult<List<CarDamageRecordListDto>> getCarDamageRecordsByCarId(int carId) throws BusinessException;

    DataResult getTotalCarDamageRecordByCarId(int carId) throws BusinessException;
}
