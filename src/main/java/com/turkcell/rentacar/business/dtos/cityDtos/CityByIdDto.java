package com.turkcell.rentacar.business.dtos.cityDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityByIdDto {

    @NotNull
    @Positive
    private int id;

    @NotNull
    @NotBlank
    private String cityName;
}
