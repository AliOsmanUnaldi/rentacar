package com.turkcell.rentacar.business.requests.additionalServiceRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {

    @NotNull
    @NotBlank
    @Size(min = 2,max = 25)
    private String additionalServiceName;

    @NotNull
    @Positive
    private double additionalServiceDailyPrice;
}
