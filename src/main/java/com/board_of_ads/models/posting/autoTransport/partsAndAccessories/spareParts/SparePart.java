package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.spareParts;

import com.board_of_ads.models.posting.autoTransport.partsAndAccessories.PartAndAccessorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_spare_parts")
public class SparePart extends PartAndAccessorie {

    private String manufacturer;

    private String PartNumber;

}
