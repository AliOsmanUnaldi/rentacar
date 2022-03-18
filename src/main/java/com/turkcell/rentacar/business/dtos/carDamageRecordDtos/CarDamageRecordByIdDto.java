package com.turkcell.rentacar.business.dtos.carDamageRecordDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageRecordByIdDto {

    private int id;

    private Integer car;

    private LocalDate accidentDate;

    private double amountOfDamageAsFinancial;
}
