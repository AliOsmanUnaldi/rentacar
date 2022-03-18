package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarDamageRecordService;
import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageRecordDtos.CarDamageRecordListDto;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.CreateCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.DeleteCarDamageRecordRequest;
import com.turkcell.rentacar.business.requests.carDamageRecordRequest.UpdateCarDamageRecordRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/carDamageRecords")
public class CarDamageRecordsController {

    private final CarDamageRecordService carDamageRecordService;

    @Autowired
    public CarDamageRecordsController(CarDamageRecordService carDamageRecordService) {

        this.carDamageRecordService = carDamageRecordService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarDamageRecordListDto>> getAll(){

        return this.carDamageRecordService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarDamageRecordRequest carDamageRecordRequest) throws BusinessException {

        return this.carDamageRecordService.add(carDamageRecordRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarDamageRecordRequest updateCarDamageRecordRequest) throws BusinessException {

        return this.carDamageRecordService.update(updateCarDamageRecordRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam @Valid DeleteCarDamageRecordRequest deleteCarDamageRecordRequest) throws BusinessException {

        return this.carDamageRecordService.delete(deleteCarDamageRecordRequest);
    }

    @GetMapping("/getCarDamageRecordByIdDtoByCarDamageRecordId")
    public DataResult<CarDamageRecordByIdDto> getCarDamageRecordByIdDtoByCarDamageRecordId(int id) throws BusinessException {

        return this.carDamageRecordService.getCarDamageRecordByIdDtoByCarDamageRecordId(id);
    }

    @GetMapping("/getCarDamageRecordsByCarId")
    DataResult<List<CarDamageRecordListDto>> getCarDamageRecordsByCarId(@RequestParam int carId){

        return this.carDamageRecordService.getCarDamageRecordsByCarId(carId);
    }

    @GetMapping("/getTotalCarDamageRecordByCarId")
    public DataResult getTotalCarDamageRecordByCarId(@RequestParam int carId) throws BusinessException {

        return this.carDamageRecordService.getTotalCarDamageRecordByCarId(carId);
    }
}
