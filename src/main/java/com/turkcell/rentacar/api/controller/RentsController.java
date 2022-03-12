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

    @PostMapping("/createRent")
    public Result add(@RequestBody @Valid CreateRentRequest createRentRequest) throws BusinessException{
        return this.rentService.add(createRentRequest);
    }

    @GetMapping("/getByRentId")
    public DataResult<RentByIdDto> getByRentId(@RequestParam int id) throws BusinessException {
        return this.rentService.getByRentId(id);
    }

    @PostMapping("/updateRent")
    public Result update(@RequestBody @Valid UpdateRentRequest updateRentRequest) throws BusinessException{
        return this.rentService.update(updateRentRequest);
    }

    @DeleteMapping("/deleteRentById")
    public Result deleteByRentId(@RequestParam int rentId) throws BusinessException {
        return this.rentService.deleteByRentId(rentId);
    }
}
