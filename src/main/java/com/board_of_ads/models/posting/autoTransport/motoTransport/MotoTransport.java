package com.board_of_ads.models.posting.autoTransport.motoTransport;

import com.board_of_ads.models.posting.autoTransport.AutoTransport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posting_moto_transport")
public class MotoTransport extends AutoTransport {
}
