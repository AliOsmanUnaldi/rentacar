package com.turkcell.rentacar.business.dtos.rentDtos;

import com.turkcell.rentacar.entities.concretes.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentByIdDto {

    private int rentId;

    private String rentedCity;

    private String deliveredCity;

    private LocalDate startDate;

    private LocalDate finishDate;

    private int carId;

    private int orderedAdditionalServiceId;

    private Integer finishKilometer;

    private Integer startKilometer;

    private Integer customer;
}
