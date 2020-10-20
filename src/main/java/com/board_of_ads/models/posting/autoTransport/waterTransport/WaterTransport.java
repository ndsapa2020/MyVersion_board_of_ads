package com.board_of_ads.models.posting.autoTransport.waterTransport;

import com.board_of_ads.models.posting.autoTransport.AutoTransport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posting_water_transport")
public class WaterTransport extends AutoTransport {
}
