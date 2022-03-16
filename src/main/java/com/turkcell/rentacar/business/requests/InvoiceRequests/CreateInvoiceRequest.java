package com.turkcell.rentacar.business.requests.InvoiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

    private String invoiceNumber;

    private Integer customer;

    private Integer rent;
}
