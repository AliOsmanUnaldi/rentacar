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
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private int rentId;

    @ManyToOne
    @JoinColumn(name = "rented_city_id",referencedColumnName = "id")
    private City rentedCity;

    @ManyToOne
    @JoinColumn(name = "delivered_city_id",referencedColumnName = "id")
    private City deliveredCity;

    @Column(name = "start_kilometer")
    private Integer startKilometer;

    @Column(name = "finish_kilometer" )
    private Integer finishKilometer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne
    @JoinColumn(name = "ordered_additional_service_id")
    private OrderedAdditionalService orderedAdditionalServices;

    @OneToMany(mappedBy = "rent")
    private List<Invoice> invoices;

}
