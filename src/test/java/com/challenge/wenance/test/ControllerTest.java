package com.challenge.wenance.test;

import com.challenge.wenance.models.dtos.BTCAvgDTO;
import com.challenge.wenance.models.dtos.BTCPriceHistoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;

public class ControllerTest extends WenanceBaseTest{

    private final String BASE_PATH = "/bitcoin/price";

    private final String AVERAGE_PATH = "/average";

    private final String FROM_PARAM = "2021-04-08 18:47:52";

    private final String TO_PARAM = "2021-04-08 18:47:54";

    private final String LAST_YEAR_FROM_PARAM = "2020-04-08 18:47:54";

    private final String LAST_YEAR_TO_PARAM = "2020-04-08 18:47:54";

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        BTCPriceHistoryDTO[] result = getMapper().readValue(
                mvcResult.getResponse().getContentAsString(), BTCPriceHistoryDTO[].class);
        Assert.assertEquals(result.length, 3);
    }

    @Test
    public void testGetBtcPriceFilteredByTimestamp() throws Exception {
        Timestamp at = Timestamp.valueOf(FROM_PARAM);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH)
                .param("at",at.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        BTCPriceHistoryDTO[] result = getMapper().readValue(
                mvcResult.getResponse().getContentAsString(), BTCPriceHistoryDTO[].class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result[0].getCreated(), at);
    }

    @Test
    public void testGetBtcPriceFilteredByTimestampInvalidParamName() throws Exception {
        Timestamp at = Timestamp.valueOf(FROM_PARAM);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH)
                .param("atTimestamp",at.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        BTCPriceHistoryDTO[] result = getMapper().readValue(
                mvcResult.getResponse().getContentAsString(), BTCPriceHistoryDTO[].class);
        Assert.assertTrue(result.length > 1);
    }

    @Test
    public void testGetBtcPriceFilteredByTimestampInternalServerError() throws Exception {
        Timestamp at = Timestamp.valueOf(LAST_YEAR_FROM_PARAM);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH)
                .param("at",at.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetBTCAveragePrice() throws Exception {
        Timestamp from = Timestamp.valueOf(FROM_PARAM);
        Timestamp to = Timestamp.valueOf(TO_PARAM);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH+AVERAGE_PATH)
                .param("from",from.toString())
                .param("to", to.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        BTCAvgDTO result = getMapper().readValue(
                mvcResult.getResponse().getContentAsString(), BTCAvgDTO.class);
        Assert.assertNotNull(result.getAvgBTCPrice());
    }

    @Test
    public void testGetBTCAveragePriceBadRequest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH+AVERAGE_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetBTCAverageInternalServerError() throws Exception {
        Timestamp invalidFrom = Timestamp.valueOf(LAST_YEAR_FROM_PARAM);
        Timestamp invalidTo = Timestamp.valueOf(LAST_YEAR_TO_PARAM);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH+AVERAGE_PATH)
                .param("from",invalidFrom.toString())
                .param("to", invalidTo.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
    }

    private ObjectMapper getMapper(){
        if(this.objectMapper == null){
            return new ObjectMapper();
        }else{
            return objectMapper;
        }
    }
}
