package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.cityDtos.CityByIdDto;
import com.turkcell.rentacar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentacar.business.requests.cityRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CityService {

    DataResult<List<CityListDto>> getAll();

    Result add(CreateCityRequest createCityRequest);

    Result update(UpdateCityRequest updateCityRequest);

    DataResult<CityByIdDto> getCityByCityId(int id);

    Result deleteCityByCityId(DeleteCityRequest deleteCityRequest);
}
