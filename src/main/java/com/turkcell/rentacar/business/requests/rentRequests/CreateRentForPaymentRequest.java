package com.turkcell.rentacar.business.requests.rentRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentForPaymentRequest {

    @NotNull
    private LocalDate startDate;

    private LocalDate finishDate;

    @NotNull
    @Positive
    private int carId;

    @NotNull
    @Positive
    private Integer rentedCity;

    @NotNull
    @Positive
    private Integer deliveredCity;

    @NotNull
    @Positive
    private Integer customer;

    private Integer startKilometer;

    private Integer finishKilometer;

}
