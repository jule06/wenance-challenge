package com.challenge.wenance.connector;

import com.challenge.wenance.models.dtos.CXOResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ICexConnector {

    CXOResponseDTO getBTCPriceFromExternal() throws JsonProcessingException;
}
