package com.example.father.msk.mypay;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mypay {


    @Id
    @GeneratedValue
    private Long id;

    private Long basicpay;

    private Long pluspay;

    private Long totalpay;

    @CreationTimestamp
    private LocalDate paydate;

    public void sum(){
        this.totalpay = this.basicpay + this.pluspay;
    }
    
}
