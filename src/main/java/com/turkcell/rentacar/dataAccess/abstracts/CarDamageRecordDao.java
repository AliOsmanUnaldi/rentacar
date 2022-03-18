package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CarDamageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDamageRecordDao extends JpaRepository<CarDamageRecord,Integer> {

    List<CarDamageRecord> getCarDamageRecordByCar_CarId(int carId);
}
