package com.turkcell.rentacar.entities.concretes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
@Table(name = "customers")
public class Customer extends User{

    @Column(name = "customer_id", insertable = false, updatable = false)
    private int customerId;

    @Column(name = "date_registered")
    private LocalDate dateRegistered;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    private List<Rent> rents;
}
