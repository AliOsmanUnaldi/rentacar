package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carDtos.CarByIdDto;
import com.turkcell.rentacar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {

    private CarDao carDao;
    private ModelMapperService modelMapperService;

    public CarManager(CarDao carDao, ModelMapperService modelMapperService) {

        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CarListDto>> getAll() {

        List<Car> result = this.carDao.findAll();

        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CarMessages.CARS_LISTED);
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) {

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.CarMessages.CAR_ADDED);
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {

        checkIfCarExists(updateCarRequest.getCarId());

        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.CarMessages.CAR_UPDATED);
    }

    @Override
    public Result deleteByCarId(int carId) throws BusinessException {

        checkIfCarExists(carId);

        this.carDao.deleteById(carId);

        return new SuccessResult(BusinessMessages.CarMessages.CAR_DELETED);
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        List<Car> result = carDao.findAll(pageable).getContent();
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {

        Sort sort;
        String value = (ascOrDesc.equals("ASC") || ascOrDesc.equals("DESC")) ? ascOrDesc : "DESC";
        sort = Sort.by(Sort.Direction.valueOf(value), "dailyPrice");

        List<Car> result = carDao.findAll(sort);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {

        List<Car> result = carDao.getByDailyPriceIsLessThanEqual(dailyPrice);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CarMessages.CARS_LISTED);
    }

    @Override
    public Car getCarByCarId(int id) {

        return this.carDao.getById(id);
    }

    @Override
    public void saveChangesForCar(Car car) {

        this.carDao.save(car);
    }

    @Override
    public DataResult<CarByIdDto> getByCarId(int carId) throws BusinessException {

        checkIfCarExists(carId);

        Car car = this.carDao.getById(carId);
        CarByIdDto response = this.modelMapperService.forDto().map(car, CarByIdDto.class);

        return new SuccessDataResult<CarByIdDto>(response,BusinessMessages.CarMessages.CAR_FOUND);
    }

    @Override
    public boolean checkIfCarExists(int carId) throws BusinessException {

        if (!carDao.existsById(carId)){

            throw new BusinessException(BusinessMessages.CarMessages.CAR_DOES_NOT_EXIST);

        }

        return true;

    }

}
