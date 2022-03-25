package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.cityDtos.CityByIdDto;
import com.turkcell.rentacar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentacar.business.requests.cityRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.cityRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CityDao;
import com.turkcell.rentacar.entities.concretes.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityManager implements CityService {

    private final CityDao cityDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<CityListDto>> getAll() {

        List<City> result = this.cityDao.findAll();
        List<CityListDto> response = result.stream()
                .map(city -> this.modelMapperService.forDto().map(city,CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CityMessages.CITIES_LISTED);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {

        City city = this.modelMapperService.forRequest().map(createCityRequest,City.class);

        cityDao.save(city);

        return new SuccessResult(BusinessMessages.CityMessages.CITY_ADDED);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {

        City city = this.modelMapperService.forRequest().map(updateCityRequest,City.class);

        this.cityDao.save(city);

        return new SuccessResult();
    }

    @Override
    public DataResult<CityByIdDto> getCityByCityId(int id) {

        City result = this.cityDao.getById(id);
        CityByIdDto response = this.modelMapperService.forDto().map(result,CityByIdDto.class);

        return new SuccessDataResult<CityByIdDto>(response,BusinessMessages.CityMessages.CITY_FOUND);
    }

    @Override
    public Result deleteCityByCityId(DeleteCityRequest deleteCityRequest) {

        this.cityDao.deleteById(deleteCityRequest.getId());

        return new SuccessResult(BusinessMessages.CityMessages.CITY_DELETED);
    }

    @Override
    public City getCityByIdWithoutDto(int id) {

        return this.cityDao.getById(id);
    }

    public boolean checkIfCityExists(int id) throws BusinessException {

        if (!this.cityDao.existsById(id)){
            throw new BusinessException(BusinessMessages.CityMessages.CITY_DOES_NOT_EXIST);
        }

        return true;
    }
}
