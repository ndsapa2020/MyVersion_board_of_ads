package com.board_of_ads.models.posting.job;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posting_job")
public class JobPosting extends Posting {

    @Column
    private String schedule;

    @Column
    private String experienceValue;

    @Column
    private String placeOfWork;
}