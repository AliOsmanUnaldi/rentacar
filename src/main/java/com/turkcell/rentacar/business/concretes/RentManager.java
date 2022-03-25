package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RentManager implements RentService {

    private final RentDao rentDao;
    private final ModelMapperService modelMapperService;
    private final CarMaintenanceService carMaintenanceService;
    private final OrderedAdditionalServiceService orderedAdditionalServiceService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final InvoiceService invoiceService;
    private final CarService carService;

    public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
                       @Lazy CarMaintenanceService carMaintenanceService,
                       @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                       @Lazy IndividualCustomerService individualCustomerService,
                       @Lazy CorporateCustomerService corporateCustomerService,
                       @Lazy InvoiceService invoiceService,
                       CarService carService) {

        this.rentDao = rentDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.invoiceService = invoiceService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<RentListDto>> getAll() {

        List<Rent> result = this.rentDao.findAll();

        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response, BusinessMessages.RentMessages.RENTS_LISTED);

    }

    @Override
    public Result addRentForIndividualCustomer(CreateRentRequest createRentRequest) throws BusinessException {


        checkIfCarIsInMaintenance(createRentRequest.getCarId());

        Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
        rent.setRentId(0);

        IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerByIndividualCustomerId(createRentRequest
                .getCustomer()).getData();
        rent.setCustomer(individualCustomer);

        this.rentDao.save(rent);

        return new SuccessResult(BusinessMessages.RentMessages.RENT_SAVED);
    }

    @Override
    public Result addRentForCorporateCustomer(CreateRentRequest createRentRequest) throws BusinessException {

        checkIfCarIsInMaintenance(createRentRequest.getCarId());

        Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
        rent.setRentId(0);

        CorporateCustomer corporateCustomer = this.corporateCustomerService.getCorporateCustomerByCorporateCustomerId(createRentRequest
                .getCustomer()).getData();
        rent.setCustomer(corporateCustomer);

        this.rentDao.save(rent);

        return new SuccessResult(BusinessMessages.RentMessages.RENT_SAVED);
    }

    @Override
    public DataResult<RentByIdDto> getRentByIdDtoByRentId(int id) throws BusinessException {

        checkIfRentExists(id);

        Rent rent = this.rentDao.getById(id);

        RentByIdDto response = this.modelMapperService.forDto().map(rent, RentByIdDto.class);

        return new SuccessDataResult<RentByIdDto>(response,BusinessMessages.RentMessages.RENT_FOUND);
    }

    @Override
    public Result updateRentForIndividualCustomer(UpdateRentRequest updateRentRequest) throws BusinessException {

        checkIfRentExists(updateRentRequest.getRentId());
        checkIfStartDayIsBeforeFinishDate(updateRentRequest.getRentId());

        Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);

        rent.setStartKilometer(updateRentRequest.getStartKilometer());
        rent.setFinishKilometer(updateRentRequest.getFinishKilometer());

        if (!checkIfFinishKmIsZeroOrNull(updateRentRequest.getFinishKilometer())){

            Car car = this.carService.getCarByCarId(updateRentRequest.getCarId());
            car.setKilometerInfo(updateRentRequest.getFinishKilometer());
            this.carService.saveChangesForCar(car);

        }

        this.rentDao.save(rent);

        return new SuccessResult(BusinessMessages.RentMessages.RENT_UPDATED);
    }

    @Override
    public Result updateRentForCorporateCustomer(UpdateRentRequest updateRentRequest) throws BusinessException {

        checkIfRentExists(updateRentRequest.getRentId());
        checkIfStartDayIsBeforeFinishDate(updateRentRequest.getRentId());

        Rent rent = this.modelMapperService.forRequest().map(updateRentRequest,Rent.class);

        rent.setStartKilometer(updateRentRequest.getStartKilometer());
        rent.setFinishKilometer(updateRentRequest.getFinishKilometer());

        if (!checkIfFinishKmIsZeroOrNull(updateRentRequest.getFinishKilometer())){

            Car car = this.carService.getCarByCarId(updateRentRequest.getCarId());
            car.setKilometerInfo(updateRentRequest.getFinishKilometer());
            this.carService.saveChangesForCar(car);

        }

        this.rentDao.save(rent);

        return new SuccessResult(BusinessMessages.RentMessages.RENT_UPDATED);
    }

    @Override
    public Result deleteByRentId(int rentId) throws BusinessException {

        checkIfRentExists(rentId);

        this.rentDao.deleteById(rentId);

        return new SuccessResult(BusinessMessages.RentMessages.RENT_DELETED);
    }

    @Override
    public DataResult<List<RentListDto>> getAllRentsByCarId(int carId) {

        List<Rent> result = this.rentDao.getAllByCar_CarId(carId);

        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent,RentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response,BusinessMessages.RentMessages.RENTS_FOUND_BY_CAR_ID);
    }

    @Override
    public DataResult<Rent> getRentByRentId(int id) {

        return new SuccessDataResult<Rent>(this.rentDao.getById(id));
    }

    @Override
    public Rent save(Rent rent) {
        this.rentDao.save(rent);
        return rent;
    }

    private void checkIfCarIsInMaintenance(int carId) throws BusinessException {

        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);

        List<CarMaintenance> response = result.getData().stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class))
                .collect(Collectors.toList());

        for (CarMaintenance carMaintenance : response
        ) {

            if (carMaintenance.getReturnDate() == null) {
                throw new BusinessException(BusinessMessages.RentMessages.CAR_IS_IN_MAINTENANCE);

            }
        }
    }

    public boolean checkIfRentExists(int rentId) throws BusinessException {

        if (!rentDao.existsById(rentId)){

            throw new BusinessException(BusinessMessages.RentMessages.RENT_NOT_FOUND);

        }
        return true;
    }

   private boolean checkIfStartDayIsBeforeFinishDate(int rentId) throws BusinessException {

       if(this.rentDao.getById(rentId).getStartDate().compareTo(this.rentDao.getById(rentId).getFinishDate()) >= 0){

            throw new BusinessException(BusinessMessages.RentMessages.INVALID_STARD_DATE);

       }
       return true;
   }

   private boolean checkIfFinishKmIsZeroOrNull(Integer kmInfo){

        if (kmInfo == 0 || kmInfo == null){

            return true;
        }

        return false;
   }
}