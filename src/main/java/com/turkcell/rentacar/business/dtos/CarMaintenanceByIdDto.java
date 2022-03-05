package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceByIdDto {

    private int carMaintenanceId;
    private String description;
    private LocalDate returnDate;
    private int carId;
}
