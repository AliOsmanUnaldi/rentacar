package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.orderedServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

    private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private final ModelMapperService modelMapperService;
    private final AdditionalServiceService additionalServiceService;

    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
                                           ModelMapperService modelMapperService,AdditionalServiceService additionalServiceService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.additionalServiceService = additionalServiceService;
    }

    @Override
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();

        List<OrderedAdditionalServiceListDto> response = result.stream()
                .map(orderedAdditionalService -> this.modelMapperService
                        .forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,"Ordered Services Listed.");
    }

    @Override
    public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
            throws BusinessException {

        Set<AdditionalService> services = new HashSet<AdditionalService>();
        for (Integer id : createOrderedAdditionalServiceRequest.getAdditionalServices()) {
            AdditionalServiceDto data = this.additionalServiceService.getById(id).getData();
            AdditionalService map = this.modelMapperService.forDto().map(data, AdditionalService.class);
            services.add(map);
        }

        System.out.println(createOrderedAdditionalServiceRequest.getAdditionalServices());

        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
                .map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
        orderedAdditionalService.setAdditionalServices(services);


        this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        return new SuccessResult("Ordered Service saved.");
    }

    @Override
    public DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException {
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);
        OrderedAdditionalServiceDto response = this.modelMapperService.forDto()
                .map(orderedAdditionalService, OrderedAdditionalServiceDto.class);
        return new SuccessDataResult<>(response,"Order Service listed.");
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
            throws BusinessException {
        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
                .map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
        this.orderedAdditionalServiceDao.save(orderedAdditionalService);

        return new SuccessResult("Ordered Service Updated");
    }

    @Override
    public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
            throws BusinessException {
        this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
        return new SuccessResult("OrderedService deleted.");
    }

    @Override
    public OrderedAdditionalService getByIdAsEntity(int id) {

        return this.orderedAdditionalServiceDao.getById(id);
    }

}
