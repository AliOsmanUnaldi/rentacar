package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paymentsController")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping("/add")
    public Result add(@RequestBody CreatePaymentRequest createPaymentRequest) throws BusinessException {

        return this.paymentService.add(createPaymentRequest);
    }
}
