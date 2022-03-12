package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{

    private final AdditionalServiceDao additionalServiceDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {

        List<AdditionalService> result = this.additionalServiceDao.findAll();
        List<AdditionalServiceListDto> response = result.stream().
                map(additionalService -> this.modelMapperService.forDto()
                        .map(additionalService, AdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<AdditionalServiceListDto>>(response,"Services listed.");
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
        AdditionalService additionalService = this.modelMapperService.forRequest()
                .map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Service is saved");
    }

    @Override
    public DataResult<AdditionalServiceDto> getById(int id) throws BusinessException {
        AdditionalService additionalService = this.additionalServiceDao.getById(id);
        AdditionalServiceDto response = this.modelMapperService.forDto()
                .map(additionalService, AdditionalServiceDto.class);

        return new SuccessDataResult<AdditionalServiceDto>(response,"Service listed.");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
        AdditionalService additionalService = this.modelMapperService.forRequest()
                .map(updateAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);

        return new SuccessResult("Service uptaded.");
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
        this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
        return new SuccessResult("Service deleted.");
    }



}