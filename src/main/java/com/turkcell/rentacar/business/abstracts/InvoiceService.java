package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;

import java.util.List;

public interface InvoiceService {

    DataResult<List<InvoiceListDto>> getAll();

    Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

    Result update(UpdateInvoiceRequest updateInvoiceRequest);

    Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

    DataResult<InvoiceByIdDto> getInvoiceDtoByInvoiceId(int invoiceId);

    Invoice save(Invoice invoice);
}
