package com.turkcell.rentacar.entities.concretes;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_additional_services")
public class OrderedAdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_service_id")
    private int orderedAdditionalServiceId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ordered_additional_services_additional_services",
            joinColumns = {
                    @JoinColumn(name = "ordered_additional_service_id"
                    )},
            inverseJoinColumns = {
                    @JoinColumn(name = "additional_service_id"
                    )})
    private Set<AdditionalService> additionalServices;

    @OneToOne(mappedBy = "orderedAdditionalServices")
    private Rent rent;
}
