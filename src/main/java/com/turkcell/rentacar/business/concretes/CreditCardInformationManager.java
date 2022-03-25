package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CreditCardInformationService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
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
    private CustomerService customerService;

    @Autowired
    public CreditCardInformationManager(CreditCardInformationDao creditCardInformationDao,ModelMapperService modelMapperService,
                                        CustomerService customerService) {
        this.creditCardInformationDao = creditCardInformationDao;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public Result add(CreateCreditCardInformationRequest createCreditCardInformationRequest) {

        this.customerService.checkIfCustomerExists(createCreditCardInformationRequest.getCustomer());

        CreditCardInformation creditCardInformation = this.modelMapperService.forRequest().map(createCreditCardInformationRequest,CreditCardInformation.class);
        Customer customer = this.customerService.getCustomerByCustomerId(createCreditCardInformationRequest.getCustomer());
        creditCardInformation.setCustomer(customer);
        this.creditCardInformationDao.save(creditCardInformation);

        return new SuccessResult(BusinessMessages.CreditCardInformationMessages.CREDIT_CARD_INFORMATION_ADDED);
    }


    @Override
    public DataResult<List<CreditCardInformationListDto>> getAll() {

        List<CreditCardInformation> result = this.creditCardInformationDao.findAll();
        List<CreditCardInformationListDto> response = result.stream()
                .map(creditCardInformation -> this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardInformationListDto>>(response,BusinessMessages.CreditCardInformationMessages.CREDIT_CARD_INFORMATIONS_LISTED);
    }

    @Override
    public DataResult<CreditCardInformationByIdDto> getCreditCardInformationDtoById(int id) {

        CreditCardInformation creditCardInformation = this.creditCardInformationDao.getById(id);
        CreditCardInformationByIdDto response = this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationByIdDto.class);

        return new SuccessDataResult<CreditCardInformationByIdDto>(response,BusinessMessages.CreditCardInformationMessages.CREDIT_CARD_INFORMATION_FOUND);
    }

    @Override
    public DataResult<List<CreditCardInformationByCustomerIdDto>> getCreditCardInformationDtoByCustomerId(int customerId) {

        this.customerService.checkIfCustomerExists(customerId);

        List<CreditCardInformation> result = this.creditCardInformationDao.getAllByCustomer_UserId(customerId);
        List<CreditCardInformationByCustomerIdDto> response = result.stream()
                .map(creditCardInformation -> this.modelMapperService.forDto().map(creditCardInformation,CreditCardInformationByCustomerIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardInformationByCustomerIdDto>>(response,BusinessMessages.CreditCardInformationMessages.CREDIT_CARD_INFORMATIONS_FOUND_BY_CUSTOMER_ID);
    }

}
