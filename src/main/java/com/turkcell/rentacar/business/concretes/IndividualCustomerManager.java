package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
    public class IndividualCustomerManager implements IndividualCustomerService {

    private final IndividualCustomerDao individualCustomerDao;
    private final ModelMapperService modelMapperService;

    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {

        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        List<IndividualCustomer> result = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> response = result.stream().
                map(individualCustomer -> this.modelMapperService.forDto()
                        .map(individualCustomer, IndividualCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<IndividualCustomerListDto>>(response, BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMERS_LISTED);
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_ADDED);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_UPDATED);
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

        this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getUserId());

        return new SuccessResult(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_DELETED);
    }

    @Override
    public DataResult<IndividualCustomerByIdDto> getIndividualCustomerByIdDtoByIndividualCustomerId(int userId) {

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(userId);

        IndividualCustomerByIdDto response = this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerByIdDto.class);

        return new SuccessDataResult<IndividualCustomerByIdDto>(response,BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_FOUND);
    }

    @Override
    public DataResult<IndividualCustomer> getIndividualCustomerByIndividualCustomerId(int userId) {

        return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.getById(userId));
    }
}
