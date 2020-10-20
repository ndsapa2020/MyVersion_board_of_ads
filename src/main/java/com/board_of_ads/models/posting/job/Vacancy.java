package com.board_of_ads.models.posting.job;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posting_job_vacancies")
public class Vacancy extends JobPosting {
}