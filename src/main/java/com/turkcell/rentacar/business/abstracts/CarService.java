package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.carDtos.CarByIdDto;
import com.turkcell.rentacar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Car;

import java.util.List;


public interface CarService {

    DataResult<List<CarListDto>> getAll();

    Result add(CreateCarRequest createCarRequest);

    Result update(UpdateCarRequest createCarRequest) throws BusinessException;

    DataResult<CarByIdDto> getByCarId(int carId) throws BusinessException;

    Result deleteByCarId(int carId) throws BusinessException;

    DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSorted(String ascOrDesc);

    DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice);

    Car getCarByCarId(int id);

    void saveChangesForCar(Car car);

    boolean checkIfCarExists(int id) throws BusinessException;
}
