package com.paypal.bfs.test.employeeserv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address implements Serializable {
    private static final long serialVersionUID = -682819989253294134L;

    @Id
    @GeneratedValue
    @Column(name="address_id")
    private Integer id;

    @Column(name="line1")
    private String line1;

    @Column(name="line2")
    private String line2;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @Column(name="zip_code")
    private Integer zipCode;
}
