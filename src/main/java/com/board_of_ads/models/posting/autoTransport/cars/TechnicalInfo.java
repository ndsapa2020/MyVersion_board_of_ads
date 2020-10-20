package com.board_of_ads.models.posting.autoTransport.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "technical_info")
public class TechnicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String color;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Short year;

    @Column
    private String carBody;

    @Column
    private Byte numberOfDoors;

    @Column
    private String generation;

    @Column
    private String typeOfEngine;

    @Column
    private String wheelDrive;

    @Column
    private String transmission;

    @Column
    private String modification;

    @Column
    private String configuration;

    @Column
    private String wheelSide;
}
