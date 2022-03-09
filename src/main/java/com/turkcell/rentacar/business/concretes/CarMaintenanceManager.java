package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private CarMaintenanceDao carMaintenanceDao;
    private ModelMapperService modelMapperService;
    private RentService rentService;

    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy RentService rentService) {
        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result = carMaintenanceDao.findAll();
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Maintenance is listed successfuly.");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        checkIfCarIsRented(createCarMaintenanceRequest);
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setId(0);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Maintenance is added.");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        checkIfCarMaintenanceExists(updateCarMaintenanceRequest.getId());
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Maintenance is updated.");
    }

    @Override
    public DataResult<CarMaintenanceByIdDto> getById(int id) throws BusinessException {
        checkIfCarMaintenanceExists(id);
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);
        return new SuccessDataResult<CarMaintenanceByIdDto>(response);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCarMaintenanceExists(id);
        this.carMaintenanceDao.deleteById(id);
        return new SuccessResult("Maintenance is deleted.");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId)  {
        List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(carId);
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Maintenances are listed.");
    }

    private boolean checkIfCarMaintenanceExists(int id) throws BusinessException {
        if (!carMaintenanceDao.existsById(id)){
            throw new BusinessException("Car maintenance info does not exist for id: ' "+id+" '.");
        }
        return true;
    }

    private void checkIfCarIsRented(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        DataResult<List<RentListDto>> result = this.rentService.getAllRentsByCarId(createCarMaintenanceRequest.getCarId());
        List<Rent> response = result.getData().stream()
                .map(rent -> this.modelMapperService.forDto().map(rent, Rent.class))
                .collect(Collectors.toList());
        for (Rent rent : response) {
            if(rent.getFinishDate() == null ||
                    (createCarMaintenanceRequest.getReturnDate().isAfter(rent.getStartDate()) &&
                            createCarMaintenanceRequest.getReturnDate().isBefore(rent.getFinishDate()))) {
                throw new BusinessException("Car has already been rented.");
            }
        }
    }
}