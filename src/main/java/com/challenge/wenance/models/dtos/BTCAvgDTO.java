package com.challenge.wenance.models.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BTCAvgDTO {

    private String avgBTCPrice;

    private String percentageDiff;
}
