package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.UpdateRentRequest;
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

        return new SuccessDataResult<List<RentListDto>>(response, "Rent infos are listed successfully.");

    }

    @Override
    public Result add(CreateRentRequest createRentRequest) throws BusinessException {
        Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);

        checkIfCarIsInMaintenance(createRentRequest.getCarId());

        this.rentDao.save(rent);

        return new SuccessResult("Rent is created.");
    }

    @Override
    public DataResult<RentByIdDto> getById(int id) {
        Rent rent = this.rentDao.getById(id);

        RentByIdDto response = this.modelMapperService.forDto().map(rent, RentByIdDto.class);

        return new SuccessDataResult<RentByIdDto>(response);
    }

    @Override
    public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
        Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);

        this.rentDao.save(rent);

        return new SuccessResult("Rent info is updated.");
    }

    @Override
    public Result deleteByRentId(int rentId) {
        this.rentDao.deleteById(rentId);
        return new SuccessResult("Rent is deleted.");
    }

    private void checkIfCarIsInMaintenance(int carId) throws BusinessException {
        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);
        List<CarMaintenance> response = result.getData().stream()
                .map(carmaintenance -> this.modelMapperService.forDto().map(carmaintenance, CarMaintenance.class))
                .collect(Collectors.toList());
        for (CarMaintenance carMaintenace : response
        ) {
            if (carMaintenace.getReturnDate() == null) {
                throw new BusinessException("Araba bakımda");
            }
        }
    }
}
