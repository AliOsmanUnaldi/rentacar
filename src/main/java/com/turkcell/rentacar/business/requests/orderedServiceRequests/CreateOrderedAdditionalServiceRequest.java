package com.turkcell.rentacar.business.requests.orderedServiceRequests;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {

    private Set<Integer> additionalServices;
}

