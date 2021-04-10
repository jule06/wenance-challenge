package com.challenge.wenance.test;

import com.challenge.wenance.models.entities.BtcPriceHistory;
import com.challenge.wenance.repositories.BtcPriceHistoryRepository;
import com.challenge.wenance.services.BTCService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

public class ServiceTest extends WenanceBaseTest{

    @Autowired
    private BTCService btcService;

    @Autowired
    private BtcPriceHistoryRepository btcPriceHistoryRepository;

    @Test
    public void testGetBTCPriceByTimestamp(){
        BtcPriceHistory btcPriceHistory = btcPriceHistoryRepository.findAll().get(0);
        Assert.assertNotNull(btcService.getBTCPrice(btcPriceHistory.getCreated().toString()));
        Assert.assertNotEquals(btcPriceHistory,btcService.getBTCPrice(btcPriceHistory.getCreated().toString()));
    }

    @Test
    public void testGetBTCPriceByTimestampNotFound(){
        Assert.assertThrows(NoSuchElementException.class, () ->
                btcService.getBTCPrice(new Timestamp(System.currentTimeMillis()).toString()));
    }

    @Test
    public void testGetBTCPriceBetweenTimestamps(){
        List<BtcPriceHistory> btcPriceHistoryList = btcPriceHistoryRepository.findAll();
        Timestamp from = btcPriceHistoryList.stream().findFirst().get().getCreated();
        Timestamp to = btcPriceHistoryList.stream().reduce((first, second) -> second).get().getCreated();
        Assert.assertNotNull(btcService.getBTCPriceBetweenTimestamps(from,to));
    }

    @Test
    public void testGetBTCPriceBetweenTimestampsNotFound(){
        BtcPriceHistory btcPriceHistory = btcPriceHistoryRepository.findAll().get(0);
        Timestamp tenMinBefore = btcPriceHistory.getCreated();
        tenMinBefore.setTime(tenMinBefore.getTime() - 600000l);
        Assert.assertThrows(NoSuchElementException.class, () ->
                btcService.getBTCPriceBetweenTimestamps(btcPriceHistory.getCreated(),tenMinBefore));
    }
}
