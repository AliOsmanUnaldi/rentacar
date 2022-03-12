package com.turkcell.rentacar.business.requests.orderedServiceRequests;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentacar.entities.concretes.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {

    @NotNull
    @Positive
    private int orderedAdditionalServiceId;

    private List<AdditionalService> additionalServiceId;

    @NotNull
    @Positive
    private int rentId;
}
