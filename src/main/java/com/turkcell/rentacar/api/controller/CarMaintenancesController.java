package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
    private CarMaintenanceService carMaintenanceService;

    public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }
    @GetMapping("/getall")
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createcarMaintenanceRequest) throws BusinessException {

        return this.carMaintenanceService.add(createcarMaintenanceRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<CarMaintenanceByIdDto> getById(@RequestParam(required = true) int carMaintenanceId) {
        return this.carMaintenanceService.getById(carMaintenanceId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/deletebyid")
    public Result deleteById(int carMaintenanceId) {
        return this.carMaintenanceService.deleteById(carMaintenanceId);
    }

    @GetMapping("/getMaintenancesByCarId")
    public DataResult<List<CarMaintenanceListDto>> getMaintenancesByCarId(@RequestParam int carId){
        return this.carMaintenanceService.getByCarId(carId);
    }
}
