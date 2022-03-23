package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "number_of_rented_days")
    private int numberOfRentedDays;

    @Column(name = "final_price")
    private double finalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id",referencedColumnName = "rent_id")
    private Rent rent;

    @ManyToOne
    private Payment payment;
}
