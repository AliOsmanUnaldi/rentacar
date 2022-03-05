package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentListDto {
    private int rentId;

    private LocalDate startDate;

    private LocalDate finishDate;

    private int carId;
}
