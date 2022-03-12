package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;

import java.util.List;

public interface OrderedAdditionalServiceService {

    DataResult<List<OrderedAdditionalServiceListDto>> getAll();

    Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;

    DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException;

    Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;

    Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;

    OrderedAdditionalService getByIdAsEntity (int id);
}
