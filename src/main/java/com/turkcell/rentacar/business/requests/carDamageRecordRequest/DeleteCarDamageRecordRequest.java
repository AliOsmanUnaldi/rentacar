package com.turkcell.rentacar.business.requests.carDamageRecordRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarDamageRecordRequest {

    @NotNull
    private int id;
}
