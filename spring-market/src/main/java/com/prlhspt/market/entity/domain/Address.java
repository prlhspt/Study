package com.prlhspt.market.entity.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Address{

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
