package com.challenge.wenance.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CXOResponseDTO implements Serializable {

    private String lprice;

    private String curr1;

    private String curr2;
}
