package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CreditCardInformationService;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByCustomerIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationByIdDto;
import com.turkcell.rentacar.business.dtos.creditCardInformationDtos.CreditCardInformationListDto;
import com.turkcell.rentacar.business.requests.creditCardRequests.CreateCreditCardInformationRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/informations")
@RequiredArgsConstructor
public class CreditCardInformationsController {

    private final CreditCardInformationService creditCardInformationService;

    @PostMapping("/add")
    public Result add(@RequestBody CreateCreditCardInformationRequest createCreditCardInformationRequest){

        return this.creditCardInformationService.add(createCreditCardInformationRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<CreditCardInformationListDto>> getAll(){

        return this.creditCardInformationService.getAll();
    }

    @GetMapping("/getCreditCardInformationDtoById")
    public DataResult<CreditCardInformationByIdDto> getCreditCardInformationDtoById(@RequestParam int id){

        return this.creditCardInformationService.getCreditCardInformationDtoById(id);
    }

    @GetMapping("/getCreditCardInformationDtoByCustomerId")
    public DataResult<List<CreditCardInformationByCustomerIdDto>> getCreditCardInformationDtoByCustomerId(@RequestParam int customerId){

        return this.creditCardInformationService.getCreditCardInformationDtoByCustomerId(customerId);
    }
}
