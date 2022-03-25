package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerManager(CustomerDao customerDao) {

        this.customerDao = customerDao;
    }

    @Override
    public Customer getCustomerByCustomerId(int userId) {

        return this.customerDao.getById(userId);

    }

    @Override
    public boolean checkIfCustomerExists(int customerId) throws BusinessException {

        if (!this.customerDao.existsById(customerId)) {

            throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_DOES_NOT_EXIST);
        }
        return true;
    }
}
