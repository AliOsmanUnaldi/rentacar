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
@Table(name = "customers")
public class Customer extends User{

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    private List<Rent> rents;
}
