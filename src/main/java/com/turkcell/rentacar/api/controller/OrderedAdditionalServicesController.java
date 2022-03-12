package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderedservices")

public class OrderedAdditionalServicesController {

    private final OrderedAdditionalServiceService orderedAdditionalServiceService;

    @Autowired
    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {

        this.orderedAdditionalServiceService = orderedAdditionalServiceService;

    }

    @GetMapping("/getall")
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll(){
        return this.orderedAdditionalServiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<OrderedAdditionalServiceDto> getById(@RequestParam int id) throws BusinessException{
        return this.orderedAdditionalServiceService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
        return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
        return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
    }


}

