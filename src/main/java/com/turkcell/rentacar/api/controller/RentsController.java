package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForIndividualCustomer;
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
    public Result addRentForIndividualCustomer(@RequestBody @Valid CreateRentRequestForIndividualCustomer createRentRequestForIndividualCustomer) throws BusinessException{

        return this.rentService.addRentForIndividualCustomer(createRentRequestForIndividualCustomer);
    }

    @PostMapping("/createRentForCorporateCustomer")
    public Result addRentForCorporateCustomer(@RequestBody @Valid CreateRentRequestForCorporateCustomer createRentRequestForCorporateCustomer) throws BusinessException{

        return this.rentService.addRentForCorporateCustomer(createRentRequestForCorporateCustomer);
    }

    @GetMapping("/getByRentId")
    public DataResult<RentByIdDto> getByRentId(@RequestParam int id) throws BusinessException {

        return this.rentService.getRentByIdDtoByRentId(id);
    }

    @PutMapping("/updateRentForIndividualCustomer")
    public Result update(@RequestBody @Valid UpdateRentRequestForCorporateCustomer updateRentRequestForCorporateCustomer) throws BusinessException{

        return this.rentService.updateRentForCorporateCustomer(updateRentRequestForCorporateCustomer);
    }

    @PutMapping("/updateRentForCorporateCustomer")
    public Result update(@RequestBody @Valid UpdateRentRequestForIndividualCustomer updateRentRequestForIndividualCustomer) throws BusinessException{

        return this.rentService.updateRentForIndividualCustomer(updateRentRequestForIndividualCustomer);
    }

    @DeleteMapping("/deleteRentById")
    public Result deleteByRentId(@RequestParam int rentId) throws BusinessException {

        return this.rentService.deleteByRentId(rentId);
    }
}
