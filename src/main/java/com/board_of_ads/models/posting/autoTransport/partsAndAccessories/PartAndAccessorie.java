package com.board_of_ads.models.posting.autoTransport.partsAndAccessories;

import com.board_of_ads.models.posting.autoTransport.AutoTransport;
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
@Table(name = "posting_part_and_accessories")
public class PartAndAccessorie extends AutoTransport {

    @Column
    private String typeOfPosting;

}
