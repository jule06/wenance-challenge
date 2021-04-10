package com.challenge.wenance.controller;

import com.challenge.wenance.models.dtos.BTCAvgDTO;
import com.challenge.wenance.models.dtos.BTCPriceHistoryDTO;
import com.challenge.wenance.services.BTCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(path = "/bitcoin/price")
@Slf4j
public class BTCController {

    @Autowired
    private BTCService btcService;

    @GetMapping("/average")
    public ResponseEntity<BTCAvgDTO> getBTCAveragePrice(@RequestParam String from, @RequestParam String to) {
        log.info("Get BTC average price service");
        BTCAvgDTO averageDTO;
        try{
            averageDTO = this.btcService.getBTCPriceBetweenTimestamps(Timestamp.valueOf(from),Timestamp.valueOf(to));
            log.info("Average calculated successfully");
        }catch (NoSuchElementException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(averageDTO);
    }

    @GetMapping()
    public ResponseEntity<List<BTCPriceHistoryDTO>> getAll(
            @RequestParam(name = "at", required = false) String at) {
        log.info("Get all service request");
        List<BTCPriceHistoryDTO> response;
        try{
            response = btcService.getBTCPrice(at);
            log.info(response.size() + " BTC Prices found");
        }catch (NoSuchElementException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
