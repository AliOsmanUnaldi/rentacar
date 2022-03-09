package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentManager implements RentService {

    private RentDao rentDao;
    private ModelMapperService modelMapperService;
    private CarMaintenanceService carMaintenanceService;

    public RentManager(RentDao rentDao, ModelMapperService modelMapperService, CarMaintenanceService carMaintenanceService) {
        this.rentDao = rentDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
    }

    @Override
    public DataResult<List<RentListDto>> getAll() {
        List<Rent> result = this.rentDao.findAll();
        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentListDto>>(response, "Rents' infos are listed successfully.");

    }

    @Override
    public Result add(CreateRentRequest createRentRequest) throws BusinessException {
        System.out.println(createRentRequest.getCarId());
        checkIfCarIsInMaintenance(createRentRequest.getCarId());
        Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);
        this.rentDao.save(rent);
        return new SuccessResult("Rent is created.");
    }

    @Override
    public DataResult<RentByIdDto> getByRentId(int id) throws BusinessException {
        checkIfRentExists(id);
        Rent rent = this.rentDao.getById(id);
        RentByIdDto response = this.modelMapperService.forDto().map(rent, RentByIdDto.class);
        return new SuccessDataResult<RentByIdDto>(response);
    }

    @Override
    public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
        checkIfRentExists(updateRentRequest.getRentId());
        Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
        this.rentDao.save(rent);
        return new SuccessResult("Rent info is updated.");
    }

    @Override
    public Result deleteByRentId(int rentId) throws BusinessException {
        checkIfRentExists(rentId);
        this.rentDao.deleteById(rentId);
        return new SuccessResult("Rent is deleted.");
    }

    @Override
    public DataResult<List<RentListDto>> getAllRentsByCarId(int carId) {
        List<Rent> result = this.rentDao.getAllByCar_CarId(carId);
        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent,RentListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentListDto>>(response,"Rents are listed for chosen car.");
    }

    private void checkIfCarIsInMaintenance(int carId) throws BusinessException {
        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);
        List<CarMaintenance> response = result.getData().stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class))
                .collect(Collectors.toList());
        for (CarMaintenance carMaintenance : response
        ) {
            if (carMaintenance.getReturnDate() == null) {
                throw new BusinessException("Araba bakımda");
            }
        }
    }

    private boolean checkIfRentExists(int rentId) throws BusinessException {
        if (!rentDao.existsById(rentId)){
            throw new BusinessException("Rent does not exist with id: ' "+rentId+" '.");
        }
        return true;
    }
}