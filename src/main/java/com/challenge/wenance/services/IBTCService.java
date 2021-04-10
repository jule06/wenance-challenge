package com.challenge.wenance.services;

import com.challenge.wenance.models.dtos.BTCAvgDTO;
import com.challenge.wenance.models.dtos.BTCPriceHistoryDTO;

import java.sql.Timestamp;
import java.util.List;

public interface IBTCService {

    List<BTCPriceHistoryDTO> getBTCPrice(String filter) throws IllegalArgumentException;

    BTCAvgDTO getBTCPriceBetweenTimestamps(Timestamp from, Timestamp to) throws IllegalArgumentException;

}

