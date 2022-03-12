package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/additionalservices")
public class AdditionalServicesController {

    private final AdditionalServiceService additionalServiceService;


    @Autowired
    public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/getall")
    public DataResult<List<AdditionalServiceListDto>> getAll(){
        return this.additionalServiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
        return this.additionalServiceService.add(createAdditionalServiceRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<AdditionalServiceDto> getById(@RequestParam int id) throws BusinessException {
        return this.additionalServiceService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
        return this.additionalServiceService.update(updateAdditionalServiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
        return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
    }

}
