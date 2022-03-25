package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CreditCardInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardInformationDao extends JpaRepository<CreditCardInformation,Integer> {
    //List<CarMaintenance> getAllByCar_CarId(Integer id);
    List<CreditCardInformation> getAllByCustomer_UserId(Integer customerId);
}
