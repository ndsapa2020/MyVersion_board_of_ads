package com.board_of_ads.models.posting.autoTransport;

import com.board_of_ads.models.posting.Posting;
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
@Table(name = "posting_auto_transport")
public class AutoTransport extends Posting {

    @Column
    private String condition;

    @Column
    private String videoURL;

    @Column
    private String contactEmail;

}
