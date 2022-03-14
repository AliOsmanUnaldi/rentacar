package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.userDtos.customerDtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualcustomers")
public class IndividualCustomersController {

    private IndividualCustomerService individualCustomerService;

    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }

    @GetMapping("/getall")
    public DataResult<List<IndividualCustomerListDto>> getAll(){

       return this.individualCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest){

        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest){

        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest){

        return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
    }

    @GetMapping("/getIndividualCustomerByUserId")
    public DataResult<IndividualCustomerByIdDto> getIndividualCustomerByUserId(int userId){

        return this.individualCustomerService.getIndividualCustomerByUserId(userId);
    }
}
