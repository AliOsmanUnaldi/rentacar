package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CreditCardInformationService;
import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByCustomerIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationListDto;
import com.turkcell.rentacar.business.requests.creditCardRequests.CreateCreditCardInformationRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CreditCardInformationDao;
import com.turkcell.rentacar.entities.concretes.CreditCardInformation;
import com.turkcell.rentacar.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardInformationManager implements CreditCardInformationService {

    private CreditCardInformationDao creditCardInformationDao;
    private ModelMapperService modelMapperService;
    private IndividualCustomerService individualCustomerService;

    @Autowired
    public CreditCardInformationManager(CreditCardInformationDao creditCardInformationDao,ModelMapperService modelMapperService,
                                        IndividualCustomerService individualCustomerService) {
        this.creditCardInformationDao = creditCardInformationDao;
        this.modelMapperService = modelMapperService;
        this.individualCustomerService = individualCustomerService;
    }

    @Override
    public Result add(CreateCreditCardInformationRequest createCreditCardInformationRequest) {

        CreditCardInformation creditCardInformation = this.modelMapperService.forRequest().map(createCreditCardInformationRequest,CreditCardInformation.class);
        Customer customer = this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(createCreditCardInformationRequest.getCustomer()).getData();
        creditCardInformation.setCustomer(customer);
        this.creditCardInformationDao.save(creditCardInformation);

        return new SuccessResult("Credit cards infos are saved successfully.");
    }


    @Override
    public DataResult<List<CreditCardInformationListDto>> getAll() {

        List<CreditCardInformation> result = this.creditCardInformationDao.findAll();
        List<CreditCardInformationListDto> response = result.stream()
                .map(creditCardInformation -> this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardInformationListDto>>(response,"Credit cards infos are listed.");
    }

    @Override
    public DataResult<CreditCardInformationByIdDto> getCreditCardInformationDtoById(int id) {

        CreditCardInformation creditCardInformation = this.creditCardInformationDao.getById(id);
        CreditCardInformationByIdDto response = this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationByIdDto.class);

        return new SuccessDataResult<CreditCardInformationByIdDto>(response,"Credit card info is found for specified id.");
    }

    @Override
    public DataResult<List<CreditCardInformationByCustomerIdDto>> getCreditCardInformationDtoByCustomerId(int customerId) {

        List<CreditCardInformation> result = this.creditCardInformationDao.getAllByCustomer_UserId(customerId);
        List<CreditCardInformationByCustomerIdDto> response = result.stream()
                .map(creditCardInformation -> this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationByCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardInformationByCustomerIdDto>>(response,"Credit cards infos are listed for specified customer.");
    }
}
