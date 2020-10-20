package com.board_of_ads.models.posting.autoTransport.partsAndAccessories.spareParts.forCar;

import com.board_of_ads.models.posting.autoTransport.partsAndAccessories.spareParts.SparePart;
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
@Table(name = "posting_engine_parts")
public class EnginePart extends SparePart {

    private String typeOfPart;
}
