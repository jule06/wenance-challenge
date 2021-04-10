package com.challenge.wenance.services;

import com.challenge.wenance.connector.CexConnector;
import com.challenge.wenance.models.dtos.BTCAvgDTO;
import com.challenge.wenance.models.dtos.BTCPriceHistoryDTO;
import com.challenge.wenance.models.dtos.CXOResponseDTO;
import com.challenge.wenance.models.entities.BtcPriceHistory;
import com.challenge.wenance.repositories.BtcPriceHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BTCService implements IBTCService {

    @Autowired
    private CexConnector cexConnector;

    @Value("${time.zone.config}")
    private String timeZoneConfig;

    @Value("${date.format.pattern}")
    private String dateFormatPattern;

    @Autowired
    private BtcPriceHistoryRepository btcPriceHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private SimpleDateFormat sdf;

    @Scheduled(initialDelayString= "${initial.delay.config}", fixedRateString = "${fixed.rate.config}")
    private void fillBTCMap(){
        try {
            CXOResponseDTO CXOResponse = cexConnector.getBTCPriceFromExternal();
            Timestamp createdTimestamp = getCurrentTimeStamp();
            BtcPriceHistory entity = BtcPriceHistory.builder()
                    .price(Double.parseDouble(CXOResponse.getLprice()))
                    .created(createdTimestamp)
                    .currency1(CXOResponse.getCurr1())
                    .currency2(CXOResponse.getCurr2()).build();
            btcPriceHistoryRepository.save(entity);
            log.info("BTC Price saved at " + createdTimestamp);
        } catch (JsonProcessingException e) {
            log.error("Error parsing response from CEX BTC Price service: ", e.getMessage());
        }
    }

    @Override
    public List<BTCPriceHistoryDTO> getBTCPrice(String at) throws NoSuchElementException{
        if(!StringUtil.isNullOrEmpty(at)){
            log.info("Searching BTC Price saved at: " + at);
            List<BtcPriceHistory> atList = btcPriceHistoryRepository.findByCreated(Timestamp.valueOf(at));
            if(atList.isEmpty()){
                throw new NoSuchElementException("No value present for this timestamp: " + at);
            }else{
                return atList.stream().map(entity -> modelMapper.map(entity, BTCPriceHistoryDTO.class)).collect(Collectors.toList());
            }
        }else{
            log.info("Getting all BTC Prices saved");
            return btcPriceHistoryRepository.findAll().stream().map(
                    entity -> modelMapper.map(entity, BTCPriceHistoryDTO.class)).collect(Collectors.toList());
        }
    }

    @Override
    public BTCAvgDTO getBTCPriceBetweenTimestamps(Timestamp from, Timestamp to) throws IllegalArgumentException{
        log.info("Searching BTC Price saved between " + from + " and " + to);
        Double averagePrice = btcPriceHistoryRepository.getPriceAverageBetweenTimeStamps(from,to)
                .orElseThrow(()-> new NoSuchElementException("No value present for these timestamps: "  + from + " and " + to));
        log.info("Computing average price..");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double maxValue = btcPriceHistoryRepository.getMaxBTCPrice();
        Double percentageDiff = ((maxValue * 100) / averagePrice)-100;

        return BTCAvgDTO.builder()
                .avgBTCPrice(decimalFormat.format(averagePrice))
                .percentageDiff(decimalFormat.format(percentageDiff))
                .build();
    }

    private Timestamp getCurrentTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZoneConfig));
        return Timestamp.valueOf(getSimpleDateFormat().format(calendar.getTime()));
    }

    private SimpleDateFormat getSimpleDateFormat(){
        if(sdf == null){
            sdf = new SimpleDateFormat(dateFormatPattern);
        }
        return sdf;
    }
}
