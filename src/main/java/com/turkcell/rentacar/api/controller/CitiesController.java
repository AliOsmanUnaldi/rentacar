package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.dtos.cityDtos.CityByIdDto;
import com.turkcell.rentacar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentacar.business.requests.cityRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citiesController")
@RequiredArgsConstructor
public class CitiesController {

    private final CityService cityService;

    @GetMapping("/getAll")
    public DataResult<List<CityListDto>> getAll(){

        return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(CreateCityRequest createCityRequest){

        return this.cityService.add(createCityRequest);
    }

    @PostMapping("/update")
    public Result update(UpdateCityRequest updateCityRequest){

        return this.cityService.update(updateCityRequest);
    }

    @GetMapping("/getCityByCityId")
    public DataResult<CityByIdDto> getCityByCityId(int id){

        return this.cityService.getCityByCityId(id);
    }

    @DeleteMapping("/deleteCityByCityId")
    public Result deleteCityByCityId(DeleteCityRequest deleteCityRequest){

        return this.cityService.deleteCityByCityId(deleteCityRequest);
    }
}
