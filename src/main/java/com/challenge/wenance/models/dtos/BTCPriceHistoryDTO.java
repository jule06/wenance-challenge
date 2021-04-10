package com.challenge.wenance.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BTCPriceHistoryDTO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires" )
    private Timestamp created;

    private String price;

    private String currency1;

    private String currency2;
}
