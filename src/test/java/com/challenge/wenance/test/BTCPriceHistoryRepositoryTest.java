package com.challenge.wenance.test;

import com.challenge.wenance.models.entities.BtcPriceHistory;
import com.challenge.wenance.repositories.BtcPriceHistoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class BTCPriceHistoryRepositoryTest extends WenanceBaseTest {

    @Autowired
    private BtcPriceHistoryRepository btcPriceHistoryRepository;

    @Test
    public void testFindAll() {
        Assert.assertNotNull(btcPriceHistoryRepository.findAll());
        Assert.assertEquals(btcPriceHistoryRepository.findAll().size(), 3);
    }

    @Test
    public void testFindByCreated() {
        Timestamp validTimeStamp = btcPriceHistoryRepository.findAll().get(0).getCreated();
        Assert.assertNotNull(btcPriceHistoryRepository.findByCreated(validTimeStamp));
    }

    @Test
    public void testGetPriceAverageBetweenTimeStamps() {
        List<BtcPriceHistory> result = btcPriceHistoryRepository.findAll();
        Timestamp validTimeStampFrom = result.get(0).getCreated();
        Timestamp validTimeStampTo = result.get(2).getCreated();
        Assert.assertNotNull(btcPriceHistoryRepository.getPriceAverageBetweenTimeStamps(validTimeStampFrom,validTimeStampTo));
    }

    @Test
    public void testGetMaxBTCPrice() {
        Assert.assertNotNull(btcPriceHistoryRepository.getMaxBTCPrice());
    }
}
