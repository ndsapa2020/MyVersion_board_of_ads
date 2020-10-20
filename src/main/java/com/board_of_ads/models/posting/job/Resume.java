package com.board_of_ads.models.posting.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posting_job_resumes")
public class Resume extends JobPosting {

    @Column
    private String education;

    @Column
    private byte age;

    @Column
    private String readyForBusinessTrip;

    @Column
    private String citizenship;

    @Column
    private String gender;

    @Column
    private String aboutYourself;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Experience> experiences;

    @OneToMany
    @JoinTable(
            name = "posting_job_resumes_languages",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<LevelLanguage> levelLanguages;
}