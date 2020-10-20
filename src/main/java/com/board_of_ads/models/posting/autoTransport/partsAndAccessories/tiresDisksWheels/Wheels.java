package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.tiresDisksWheels;

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
@Table(name = "posting_wheels")
public class Wheels extends DisksAbstract {

    @Column
    private String season;

    @Column
    private Short profileWidth;

    @Column
    private Short profileHeight;
}
