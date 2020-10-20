package com.board_of_ads.models.posting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//Пост с недвижимостью

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_estate")
public class EstatePosting extends Posting {

    //Количество комнат
    @Column
    private Integer countRoom;
    //Является ли собственником
    @Column
    private boolean isProprietor;
}
