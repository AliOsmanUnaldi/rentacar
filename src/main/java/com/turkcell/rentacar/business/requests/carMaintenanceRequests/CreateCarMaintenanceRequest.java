package com.turkcell.rentacar.business.requests.carMaintenanceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

    private String description;
    private LocalDate returnDate;

    @NotNull
    @Positive
    private int carId;
}
