package com.turkcell.rentacar.business.requests.carRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    @Min(0)
    @NotNull
    private double dailyPrice;

    @Min(1950)
    @NotNull
    private int modelYear;

    private String description;

    @NotNull
    private int brandId;

    @NotNull
    private int colorId;
}
