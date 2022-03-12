package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.brandDtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;


public interface BrandService {
    DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<BrandByIdDto> getByBrandId(int id) throws BusinessException;

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result deleteById(int brandId) throws BusinessException;
}
