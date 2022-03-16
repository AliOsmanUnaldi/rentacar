package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.InvoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.InvoiceRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "api/invoices")
@RequiredArgsConstructor
public class InvoicesController {

    private final InvoiceService invoiceService;

    @GetMapping("/getAll")
    public DataResult<List<InvoiceListDto>> getAll(){

        return this.invoiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException{

        return this.invoiceService.add(createInvoiceRequest);
    }

    @PutMapping("/update")
    public Result update(UpdateInvoiceRequest updateInvoiceRequest){

        return this.invoiceService.update(updateInvoiceRequest);
    }


    @DeleteMapping("/delete")
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest){

        return this.invoiceService.delete(deleteInvoiceRequest);
    }

    @GetMapping("/getById")
    public DataResult<InvoiceByIdDto> getInvoiceDtoByInvoiceId(int invoiceId){

        return this.invoiceService.getInvoiceDtoByInvoiceId(invoiceId);
    }
}
