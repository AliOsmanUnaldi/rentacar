package com.turkcell.rentacar.business.requests.InvoiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteInvoiceRequest {

    private int invoiceId;
}
