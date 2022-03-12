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
public class UpdateRentRequest {
    @NotNull
    private int rentId;

    @NotNull
    private LocalDate startDate;

    private LocalDate finishDate;

    @NotNull
    @Positive
    private int carId;


}
