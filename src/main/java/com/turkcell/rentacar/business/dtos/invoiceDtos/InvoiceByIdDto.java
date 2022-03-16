package com.turkcell.rentacar.business.dtos.invoiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceByIdDto {

    private int invoiceId;

    private String invoiceNumber;

    private LocalDate creationDate;

    private LocalDate startDate;

    private LocalDate finishDate;

    private int numberOfRentedDays;

    private double finalPrice;

    private Integer customerId;

    private Integer rentId;
}
