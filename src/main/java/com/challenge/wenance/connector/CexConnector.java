package com.challenge.wenance.connector;

import com.challenge.wenance.models.dtos.CXOResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CexConnector implements ICexConnector {

    @Value("${cex.host}")
    private String cexHost;

    @Value("${cex.uri}")
    private String cexUri;

    @Override
    public CXOResponseDTO getBTCPriceFromExternal() throws JsonProcessingException {
        WebClient client = WebClient.create(cexHost);
        String stringResponse = client.get()
                .uri(cexUri)
                .retrieve()
                .bodyToMono(String.class).block();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringResponse, CXOResponseDTO.class);
    }
}