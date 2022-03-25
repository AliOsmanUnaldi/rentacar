package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {

    private BrandDao brandDao;
    private ModelMapperService modelMapperService;

    public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {

        this.brandDao = brandDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<BrandListDto>> getAll() {

        List<Brand> result = this.brandDao.findAll();

        List<BrandListDto> response = result.stream()
                .map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<BrandListDto>>(response, BusinessMessages.BrandMessages.BRANDS_LISTED);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        checkIfBrandNameIsUnique(brand.getBrandName());

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.BrandMessages.BRAND_ADDED);
    }


    @Override
    public DataResult<BrandByIdDto> getByBrandId(int brandId) throws BusinessException {

        checkIfBrandExists(brandId);

        Brand brand = this.brandDao.getById(brandId);

        BrandByIdDto response = this.modelMapperService.forDto().map(brand, BrandByIdDto.class);

        return new SuccessDataResult<BrandByIdDto>(response, BusinessMessages.BrandMessages.BRAND_FOUND);
    }


    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {

        checkIfBrandExists(updateBrandRequest.getBrandId());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        checkIfBrandNameIsUnique(brand.getBrandName());

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.BrandMessages.BRAND_UPDATED);
    }

    @Override
    public Result deleteById(int brandId) throws BusinessException {

        checkIfBrandExists(brandId);

        this.brandDao.deleteById(brandId);

        return new SuccessResult(BusinessMessages.BrandMessages.BRAND_DELETED);

    }

    private boolean checkIfBrandNameIsUnique(String brandName) throws BusinessException {

        for (BrandListDto brandElement : this.getAll().getData()) {

            if (brandElement.getBrandName().equals(brandName)) {

                throw new BusinessException(BusinessMessages.BrandMessages.UNIQUE_BRAND_NAME);

            }
        }

        return true;
    }

    private boolean checkIfBrandExists(int brandId) throws BusinessException {

        if (!brandDao.existsById(brandId)){

            throw new BusinessException(BusinessMessages.BrandMessages.BRAND_DOES_NOT_EXIST);

        }

        return true;
    }


}
