package com.turkcell.rentacar.business.requests.colorRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {

    private String colorName;
}
