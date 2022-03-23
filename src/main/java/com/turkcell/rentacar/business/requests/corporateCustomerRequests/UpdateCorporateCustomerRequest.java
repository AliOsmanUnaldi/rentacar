package com.turkcell.rentacar.business.requests.corporateCustomerRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

    @NotNull
    @Min(1)
    private int userId;

    @NotNull
   // @Email
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String companyName;

    @NotNull
    @NotBlank
    private String taxNumber;

    private LocalDate dateRegistered;

}
