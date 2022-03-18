package com.turkcell.rentacar.business.requests.carDamageRecordRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRecordRequest {

    @NotNull
    private Integer car;

    private LocalDate accidentDate;

    @Positive
    private double amountOfDamageAsFinancial;
}
