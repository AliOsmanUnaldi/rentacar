package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private CarMaintenanceDao carMaintenanceDao;
    private ModelMapperService modelMapperService;
    private RentService rentService;
    private CarService carService;

    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy RentService rentService,
                                 CarService carService) {

        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {

        List<CarMaintenance> result = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCES_LISTED);
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

        this.carService.checkIfCarExists(createCarMaintenanceRequest.getCarId());
        checkIfCarIsRented(createCarMaintenanceRequest);

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setId(0);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_ADDED);
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

        this.carService.checkIfCarExists(updateCarMaintenanceRequest.getCarId());
        checkIfCarMaintenanceExists(updateCarMaintenanceRequest.getId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_UPDATED);
    }

    @Override
    public DataResult<CarMaintenanceByIdDto> getById(int id) throws BusinessException {

        this.carService.checkIfCarExists(this.carMaintenanceDao.getById(id).getCar().getCarId());
        checkIfCarMaintenanceExists(id);

        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);

        return new SuccessDataResult<CarMaintenanceByIdDto>(response, BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_FOUND);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {

        this.carService.checkIfCarExists(this.carMaintenanceDao.getById(id).getCar().getCarId());
        checkIfCarMaintenanceExists(id);

        this.carMaintenanceDao.deleteById(id);

        return new SuccessResult(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_DELETED);
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {

        this.carService.checkIfCarExists(carId);

        List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(carId);
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_FOUND);
    }

    private boolean checkIfCarMaintenanceExists(int id) throws BusinessException {

        this.carService.checkIfCarExists(this.carMaintenanceDao.getById(id).getCar().getCarId());

        if (!carMaintenanceDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_DOES_NOT_EXIST);

        }

        return true;
    }

    private void checkIfCarIsRented(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

        this.carService.checkIfCarExists(createCarMaintenanceRequest.getCarId());

        DataResult<List<RentListDto>> result = this.rentService.getAllRentsByCarId(createCarMaintenanceRequest.getCarId());
        List<Rent> response = result.getData().stream()
                .map(rent -> this.modelMapperService.forDto().map(rent, Rent.class))
                .collect(Collectors.toList());

        for (Rent rent : response) {

            if (rent.getFinishDate() == null ||
                    (createCarMaintenanceRequest.getReturnDate().isAfter(rent.getStartDate()) &&
                            createCarMaintenanceRequest.getReturnDate().isBefore(rent.getFinishDate()))) {

                throw new BusinessException(BusinessMessages.CarMaintenanceMessages.CAR_RENTED_ALREADY);

            }
        }
    }
}