package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
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
    public boolean checkIfCustomerExists(int customerId) {
        return true ? this.customerDao.existsById(customerId) : false;
    }
}
