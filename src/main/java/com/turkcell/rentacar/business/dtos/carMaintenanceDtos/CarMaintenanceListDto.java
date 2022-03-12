package com.turkcell.rentacar.business.dtos.carMaintenanceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {

    private int id;
    private String description;
    private LocalDate returnDate;
    private int carCarId;
}
