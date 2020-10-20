package com.board_of_ads.models.posting.autoTransport.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_used_cars")
public class UsedCar extends Car {

    @Column
    private String typeOfPosting;

    @Column
    private String StateNumber;

    @Column
    private Integer mileage;

    @Column
    private Byte numberOfOwners;
}
