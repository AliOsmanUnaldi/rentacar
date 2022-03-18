package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(name = "/api/rents")
public class RentsController {

    private RentService rentService;

    public RentsController(RentService rentService) {

        this.rentService = rentService;
    }

    @GetMapping("/getAllRents")
    public DataResult<List<RentListDto>> getAll(){

        return this.rentService.getAll();
    }

    @PostMapping("/createRentForIndividualCustomer")
    public Result addRentForIndividualCustomer(@RequestBody @Valid CreateRentRequest createRentRequest) throws BusinessException{

        return this.rentService.addRentForIndividualCustomer(createRentRequest);
    }

    @PostMapping("/createRentForCorporateCustomer")
    public Result addRentForCorporateCustomer(@RequestBody @Valid CreateRentRequest createRentRequest) throws BusinessException{

        return this.rentService.addRentForCorporateCustomer(createRentRequest);
    }

    @GetMapping("/getByRentId")
    public DataResult<RentByIdDto> getByRentId(@RequestParam int id) throws BusinessException {

        return this.rentService.getRentByIdDtoByRentId(id);
    }

    @PutMapping("/updateRentForIndividualCustomer")
    public Result updateRentForCorporateCustomer(@RequestBody @Valid UpdateRentRequest updateRentRequest) throws BusinessException{

        return this.rentService.updateRentForCorporateCustomer(updateRentRequest);
    }

    @PutMapping("/updateRentForCorporateCustomer")
    public Result updateRentForIndividualCustomer(@RequestBody @Valid UpdateRentRequest updateRentRequest) throws BusinessException{

        return this.rentService.updateRentForIndividualCustomer(updateRentRequest);
    }

    @DeleteMapping("/deleteRentById")
    public Result deleteByRentId(@RequestParam int rentId) throws BusinessException {

        return this.rentService.deleteByRentId(rentId);
    }
}
