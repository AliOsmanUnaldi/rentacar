package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "corporate_customer_id", referencedColumnName = "customer_id")
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer{

    @Column(name = "corporate_customer_id", insertable = false, updatable = false)
    private int corporateCustomerId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tax_number")
    private String taxNumber;

}
