package com.prlhspt.market.domain.Item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item {

    private String artist;
    private String etc;

}
