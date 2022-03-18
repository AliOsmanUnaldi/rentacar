package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "individual_customer_id", referencedColumnName = "customer_id")
@Table(name = "individual_customers")
public class IndividualCustomer extends Customer{

    @Column(name = "individual_customer_id", insertable = false, updatable = false)
    private int individualCustomerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "national_identity")
    private String nationalIdentity;


}
