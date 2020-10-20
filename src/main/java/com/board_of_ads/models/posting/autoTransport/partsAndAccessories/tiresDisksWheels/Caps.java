package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.tiresDisksWheels;

import com.board_of_ads.models.posting.autoTransport.partsAndAccessories.PartAndAccessorie;
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
@Table(name = "posting_caps")
public class Caps extends PartAndAccessorie {

    @Column
    private Byte diameter;

}
