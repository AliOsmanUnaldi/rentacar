package com.turkcell.rentacar.business.requests.InvoiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

    private int invoiceId;

    private String invoiceNumber;

    private LocalDate creationDate;

    private LocalDate startDate;

    private LocalDate finishDate;

    private int numberOfRentedDays;

    private double finalPrice;

    private Integer customer;

    private Integer rent;
}
