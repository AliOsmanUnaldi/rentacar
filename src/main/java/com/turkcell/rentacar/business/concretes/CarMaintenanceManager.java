package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private CarMaintenanceDao carMaintenanceDao;
    private ModelMapperService modelMapperService;

    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService) {
        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
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
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
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
    public DataResult<CarMaintenanceByIdDto> getByCarMaintenanceId(int carMaintenanceId) throws BusinessException {
        checkIfCarMaintenanceExists(carMaintenanceId);
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);
        return new SuccessDataResult<CarMaintenanceByIdDto>(response);
    }

    @Override
    public Result deleteById(int carMaintenanceId) throws BusinessException {
        checkIfCarMaintenanceExists(carMaintenanceId);
        this.carMaintenanceDao.deleteById(carMaintenanceId);
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
}
