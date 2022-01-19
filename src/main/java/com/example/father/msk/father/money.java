package com.example.father.msk.father;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class money {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    
    private int month;

    private LocalDate datememo;

    @Column(name = "companyPrice")
    private Long companyprice;

    @Column(name = "myPrice")
    private Long myprice;

    @Column(name = "totalPrice")
    private Long totalprice;

    
}
