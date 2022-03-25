package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByCustomerIdDto;
import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentsController")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping("/add")
    public Result add(@RequestBody CreatePaymentRequest createPaymentRequest) throws BusinessException {

        return this.paymentService.add(createPaymentRequest);
    }

    @GetMapping("/getAll")
    public DataResult getAllPayments(){

        return this.paymentService.getAllPayments();
    }

    @GetMapping("/getByPaymentId")
    public DataResult getPaymentDtoByPaymentId(@RequestParam int id){

        return this.paymentService.getPaymentDtosByPaymentId(id);
    }

    @GetMapping("/getPaymentDtoSByCustomerId")
    public DataResult<List<PaymentByCustomerIdDto>> getPaymentDtoSByCustomerId(@RequestParam int customerId){

        return this.paymentService.getPaymentDtoSByCustomerId(customerId);
    }
}
