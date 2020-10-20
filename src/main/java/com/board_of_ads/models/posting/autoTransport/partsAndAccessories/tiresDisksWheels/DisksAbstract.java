package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.tiresDisksWheels;

import com.board_of_ads.models.posting.autoTransport.partsAndAccessories.PartAndAccessorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class DisksAbstract extends PartAndAccessorie {

    @Column
    private Byte diameter;

    @Column
    private String typeOfDisk;

    @Column
    private Byte diskWidth;

    @Column
    private Byte numberOfHoles;

    @Column
    private Short diameterOfHolesPlacement;

    @Column
    private Short vinos;
}
