package com.turkcell.rentacar.business.dtos.rentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentByIdDto {

    private LocalDate startDate;

    private LocalDate finishDate;

    private int carId;
}
