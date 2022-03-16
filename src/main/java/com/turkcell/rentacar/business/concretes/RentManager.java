package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentByIdDto;
import com.turkcell.rentacar.business.dtos.rentDtos.RentListDto;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.CreateRentRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.rentRequests.UpdateRentRequestForIndividualCustomer;
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

    public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
                       @Lazy CarMaintenanceService carMaintenanceService,
                       @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                       @Lazy IndividualCustomerService individualCustomerService,
                       @Lazy CorporateCustomerService corporateCustomerService,
                       @Lazy InvoiceService invoiceService) {

        this.rentDao = rentDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.invoiceService = invoiceService;
    }

    @Override
    public DataResult<List<RentListDto>> getAll() {

        List<Rent> result = this.rentDao.findAll();

        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response, "Rents' infos are listed successfully.");

    }

    @Override
    public Result addRentForIndividualCustomer(CreateRentRequestForIndividualCustomer createRentRequestForIndividualCustomer) throws BusinessException {


        checkIfCarIsInMaintenance(createRentRequestForIndividualCustomer.getCarId());

        Rent rent = this.modelMapperService.forDto().map(createRentRequestForIndividualCustomer, Rent.class);
        rent.setRentId(0);

        IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerByUserId(createRentRequestForIndividualCustomer
                .getIndividualCustomer()).getData();
        rent.setIndividualCustomer(individualCustomer);
        rent.setCorporateCustomer(null);

        this.rentDao.save(rent);

        return new SuccessResult("Rent is created for individual customer.");
    }

    @Override
    public Result addRentForCorporateCustomer(CreateRentRequestForCorporateCustomer createRentRequestForCorporateCustomer) throws BusinessException {

        checkIfCarIsInMaintenance(createRentRequestForCorporateCustomer.getCarId());

        Rent rent = this.modelMapperService.forDto().map(createRentRequestForCorporateCustomer, Rent.class);
        rent.setRentId(0);

        CorporateCustomer corporateCustomer = this.corporateCustomerService.getCorporateCustomerByUserId(createRentRequestForCorporateCustomer
                .getCorporateCustomer()).getData();
        rent.setCorporateCustomer(corporateCustomer);
        rent.setIndividualCustomer(null);

        this.rentDao.save(rent);

        return new SuccessResult("Rent is created for corporate customer.");
    }

    @Override
    public DataResult<RentByIdDto> getRentByIdDtoByRentId(int id) throws BusinessException {

        checkIfRentExists(id);

        Rent rent = this.rentDao.getById(id);

        RentByIdDto response = this.modelMapperService.forDto().map(rent, RentByIdDto.class);

        return new SuccessDataResult<RentByIdDto>(response,"Rent found by given id : "+id);
    }

    @Override
    public Result updateRentForIndividualCustomer(UpdateRentRequestForIndividualCustomer updateRentRequestForIndividualCustomer) throws BusinessException {

        checkIfRentExists(updateRentRequestForIndividualCustomer.getRentId());
        checkIfStartDayIsBeforeFinishDate(updateRentRequestForIndividualCustomer.getRentId());

        Rent rent = this.modelMapperService.forRequest().map(updateRentRequestForIndividualCustomer, Rent.class);

        this.rentDao.save(rent);

        return new SuccessResult("Rent info is updated for individual customer.");
    }

    @Override
    public Result updateRentForCorporateCustomer(UpdateRentRequestForCorporateCustomer updateRentRequestForCorporateCustomer) throws BusinessException {

        checkIfRentExists(updateRentRequestForCorporateCustomer.getRentId());
        checkIfStartDayIsBeforeFinishDate(updateRentRequestForCorporateCustomer.getRentId());

        Rent rent = this.modelMapperService.forRequest().map(updateRentRequestForCorporateCustomer,Rent.class);

        this.rentDao.save(rent);

        return new SuccessResult("Rent info is updated for corporate customer.");
    }

    @Override
    public Result deleteByRentId(int rentId) throws BusinessException {

        checkIfRentExists(rentId);

        this.rentDao.deleteById(rentId);

        return new SuccessResult("Rent is deleted.");
    }

    @Override
    public DataResult<List<RentListDto>> getAllRentsByCarId(int carId) {

        List<Rent> result = this.rentDao.getAllByCar_CarId(carId);

        List<RentListDto> response = result.stream()
                .map(rent -> this.modelMapperService.forDto().map(rent,RentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response,"Rents are listed for chosen car.");
    }

    @Override
    public DataResult<Rent> getRentByRentId(int id) {

        return new SuccessDataResult<Rent>(this.rentDao.getById(id));
    }

    private void checkIfCarIsInMaintenance(int carId) throws BusinessException {

        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);

        List<CarMaintenance> response = result.getData().stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class))
                .collect(Collectors.toList());

        for (CarMaintenance carMaintenance : response
        ) {

            if (carMaintenance.getReturnDate() == null) {
                throw new BusinessException("Araba bakımda");

            }
        }
    }

    private boolean checkIfRentExists(int rentId) throws BusinessException {

        if (!rentDao.existsById(rentId)){

            throw new BusinessException("Rent does not exist with id: ' "+rentId+" '.");

        }
        return true;
    }

   private boolean checkIfStartDayIsBeforeFinishDate(int rentId) throws BusinessException {

       if(this.rentDao.getById(rentId).getStartDate().compareTo(this.rentDao.getById(rentId).getFinishDate()) >= 0){

            throw new BusinessException("Start date cannot be later than finish date!");

       }
       return true;
   }
}