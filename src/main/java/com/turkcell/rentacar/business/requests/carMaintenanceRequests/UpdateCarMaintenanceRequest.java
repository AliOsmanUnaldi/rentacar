package com.turkcell.rentacar.business.requests.carMaintenanceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
    private int id;
    private String description;
    private LocalDate returnDate;
    private int carId;
}
