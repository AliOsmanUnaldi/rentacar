package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "card_owner")
    private String cardOwner;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_expire_date")
    private LocalDate cardExpiryDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ordered_additional_service_id")
    private OrderedAdditionalService orderedAdditionalService;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Invoice> invoices;

    @Column(name = "total_price")
    private double totalPrice;
}
