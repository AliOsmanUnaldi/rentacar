package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarDamageRecordService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordListDto;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.CreateCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.DeleteCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.UpdateCarDamageRecordRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDamageRecordDao;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.CarDamageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageRecordManager implements CarDamageRecordService {

    private final CarDamageRecordDao carDamageRecordDao;
    private final ModelMapperService modelMapperService;
    private final CarService carService;

    @Autowired
    public CarDamageRecordManager(CarDamageRecordDao carDamageRecordDao, ModelMapperService modelMapperService,
                                  @Lazy CarService carService) {

        this.carDamageRecordDao = carDamageRecordDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }


    @Override
    public DataResult<List<CarDamageRecordListDto>> getAll() {

        List<CarDamageRecord> result = this.carDamageRecordDao.findAll();
        List<CarDamageRecordListDto> response = result.stream()
                .map(carDamageRecord -> this.modelMapperService.forDto().map(carDamageRecord, CarDamageRecordListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarDamageRecordListDto>>(response, BusinessMessages.CarDamageRecordMessages.CAR_DAMAGES_LISTED);
    }

    @Override
    public Result add(CreateCarDamageRecordRequest createCarDamageRecordRequest) throws BusinessException {

        this.carService.checkIfCarExists(createCarDamageRecordRequest.getCar());

        CarDamageRecord carDamageRecord = this.modelMapperService.forRequest().map(createCarDamageRecordRequest, CarDamageRecord.class);
        carDamageRecord.setCar(this.carService.getCarByCarId(createCarDamageRecordRequest.getCar()));

        Car car = this.carService.getCarByCarId(createCarDamageRecordRequest.getCar());
        car.getCarDamageRecords().add(carDamageRecord);

        this.carDamageRecordDao.save(carDamageRecord);
        this.carService.saveChangesForCar(car);


        return new SuccessResult(BusinessMessages.CarDamageRecordMessages.CAR_DAMAGE_ADDED);
    }

    @Override
    public Result update(UpdateCarDamageRecordRequest updateCarDamageRecordRequest) throws BusinessException {

        checkIfCarDamageRecordExists(updateCarDamageRecordRequest.getId());
        this.carService.checkIfCarExists(updateCarDamageRecordRequest.getCar());

        CarDamageRecord carDamageRecord = this.modelMapperService.forRequest().map(updateCarDamageRecordRequest, CarDamageRecord.class);

        Car car = this.carService.getCarByCarId(updateCarDamageRecordRequest.getCar());
        car.getCarDamageRecords().add(carDamageRecord);

        this.carService.saveChangesForCar(car);
        this.carDamageRecordDao.save(carDamageRecord);

        return new SuccessResult(BusinessMessages.CarDamageRecordMessages.CAR_DAMAGE_UPDATED);
    }

    @Override
    public Result delete(DeleteCarDamageRecordRequest deleteCarDamageRecordRequest) throws BusinessException {

        this.carService.checkIfCarExists(this.carDamageRecordDao.getById(deleteCarDamageRecordRequest.getId()).getCar().getCarId());
        checkIfCarDamageRecordExists(deleteCarDamageRecordRequest.getId());

        this.carDamageRecordDao.deleteById(deleteCarDamageRecordRequest.getId());

        return new SuccessResult(BusinessMessages.CarDamageRecordMessages.CAR_DAMAGE_DELETED);
    }

    @Override
    public DataResult<CarDamageRecordByIdDto> getCarDamageRecordByIdDtoByCarDamageRecordId(int id) throws BusinessException {

        this.carService.checkIfCarExists(this.carDamageRecordDao.getById(id).getCar().getCarId());
        checkIfCarDamageRecordExists(id);

        CarDamageRecord carDamageRecord = this.carDamageRecordDao.getById(id);
        CarDamageRecordByIdDto response = this.modelMapperService.forDto().map(carDamageRecord, CarDamageRecordByIdDto.class);

        return new SuccessDataResult<CarDamageRecordByIdDto>(response, BusinessMessages.CarDamageRecordMessages.CAR_DAMAGE_FOUND);
    }

    @Override
    public DataResult<List<CarDamageRecordListDto>> getCarDamageRecordsByCarId(int carId) throws BusinessException {

        this.carService.checkIfCarExists(carId);

        List<CarDamageRecord> carDamageRecordList = this.carDamageRecordDao.getCarDamageRecordByCar_CarId(carId);
        List<CarDamageRecordListDto> response = carDamageRecordList.stream()
                .map(carDamageRecord -> this.modelMapperService.forDto().map(carDamageRecord, CarDamageRecordListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarDamageRecordListDto>>(response, BusinessMessages.CarDamageRecordMessages.CAR_DAMAGES_FOUND_BY_CAR_ID);
    }

    @Override
    public DataResult getTotalCarDamageRecordByCarId(int carId) throws BusinessException {

        this.carService.checkIfCarExists(carId);

        double totalAmountOfCarDamageRecords = 0;

        for (CarDamageRecordListDto carDamageRecordListDto : getCarDamageRecordsByCarId(carId).getData()
        ) {
            totalAmountOfCarDamageRecords += carDamageRecordListDto.getAmountOfDamageAsFinancial();
        }

        return new SuccessDataResult(totalAmountOfCarDamageRecords);
    }

    private boolean checkIfCarDamageRecordExists(int id) throws BusinessException {

        if (!this.carDamageRecordDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.CarDamageRecordMessages.CAR_DAMAGE_DOES_NOT_EXIST);

        }
        return true;
    }
}
