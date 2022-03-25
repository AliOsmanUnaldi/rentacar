package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.entities.concretes.Customer;

public interface CustomerService {

    Customer getCustomerByCustomerId(int userId);
    boolean checkIfCustomerExists(int customerId);
}
