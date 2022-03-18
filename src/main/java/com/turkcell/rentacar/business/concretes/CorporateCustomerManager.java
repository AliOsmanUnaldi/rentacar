package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.corporateCustomerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final CorporateCustomerDao corporateCustomerDao;
    private final ModelMapperService modelMapperService;

    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {

        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> response = result.stream().
                map(corporateCustomer -> this.modelMapperService.forDto()
                        .map(corporateCustomer, CorporateCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CorporateCustomerListDto>>(response,"Corporate customers listed.");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate customer "+ corporateCustomer.getCompanyName()+" is added.");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate customer "+corporateCustomer.getCompanyName()+" is updated.");
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {

        this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getUserId()); //userdan silmen gerekebilir

        return new SuccessResult("Corporate customer is deleted.");
    }

    @Override
    public DataResult<CorporateCustomerByIdDto> getCorporateCustomerByIdDtoByCorporateCustomerId(int userId) {

        CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(userId);

        CorporateCustomerByIdDto response = this.modelMapperService.forDto().map(corporateCustomer, CorporateCustomerByIdDto.class);

        return new SuccessDataResult<CorporateCustomerByIdDto>(response,"Corporate customer found by given id : "+userId);
    }

    @Override
    public DataResult<CorporateCustomer> getCorporateCustomerByCorporateCustomerId(int userId) {

        return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.getById(userId));
    }
}
