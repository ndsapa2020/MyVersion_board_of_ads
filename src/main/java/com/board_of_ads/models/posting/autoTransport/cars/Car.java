package com.board_of_ads.models.posting.autoTransport.cars;

import com.board_of_ads.models.posting.autoTransport.AutoTransport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_cars")
public class Car extends AutoTransport {

    @Column
    private String VIN;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="technicalInfo_id")
    private TechnicalInfo technicalInfo;

    @Column
    private boolean hasServiceBook;

    @Column
    private boolean hasDealerService;

    @Column
    private boolean UnderWarranty;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    private Set<Option> additionalOptions;
}
