package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.tiresDisksWheels;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posting_disks")
public class Disks extends DisksAbstract {
}
