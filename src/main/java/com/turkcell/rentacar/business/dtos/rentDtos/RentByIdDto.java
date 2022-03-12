package com.turkcell.rentacar.business.dtos.rentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentByIdDto {

    private int rentId;
    private String rentedCity;
    private String deliveredCity;
    private LocalDate startDate;
    private LocalDate finishDate;
    private double bill;
    private int carId;
    private int orderedAdditionalServiceId;
}
