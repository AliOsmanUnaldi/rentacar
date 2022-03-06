package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.carDtos.CarByIdDto;
import com.turkcell.rentacar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")

public class CarsController {
    private CarService carService;

    public CarsController(CarService carService) {

        this.carService = carService;
    }

    @GetMapping("/getall")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarRequest createcarRequest) throws BusinessException {

        return this.carService.add(createcarRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<CarByIdDto> getById(@RequestParam(required = true) int carId) throws BusinessException {
        return this.carService.getByCarId(carId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCarRequest updatecarRequest) throws BusinessException {
        return this.carService.update(updatecarRequest);
    }

    @DeleteMapping("/deletebyid")
    public Result deleteById(@RequestParam int carId) throws BusinessException {
        return this.carService.deleteByCarId(carId);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        return this.carService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
        return this.carService.getAllSorted(ascOrDesc);
    }

    @GetMapping("/sortAllByDailyPrice")
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        return this.carService.getByDailyPriceIsLessThanEqual(dailyPrice);
    }

}
