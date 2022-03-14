package com.turkcell.rentacar.business.requests.rentRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentRequest {

    @NotNull
    private LocalDate startDate;

    private LocalDate finishDate;

    @NotNull
    @Positive
    private int carId;

    @NotNull
    @Positive
    private Integer orderedAdditionalServiceId;

    @NotNull
    @Positive
    private Integer rentedCity;

    @NotNull
    @Positive
    private Integer deliveredCity;
}
