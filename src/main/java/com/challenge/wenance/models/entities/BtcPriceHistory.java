package com.challenge.wenance.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "btc_price_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BtcPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="created")
    private Timestamp created;

    @Column(name = "price")
    private double price;

    @Column(name = "currency_1")
    private String currency1;

    @Column(name = "currency_2")
    private String currency2;
}
