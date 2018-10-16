package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.services.RestaurantTableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestaurantTableController.class, secure = false)
public class RestaurantTableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantTableService restaurantTableService;

    private RestaurantTable mockTable = RestaurantTable.builder()
                                                       .id(1)
                                                       .personAmount(4)
                                                       .reservation(null)
                                                       .build();

    @Test
    public void retrieveSpecificTable() throws Exception {
        // given
        when(restaurantTableService.getTableById(Mockito.anyLong()))
                .thenReturn(mockTable);
        MockHttpServletRequestBuilder requestBuilder = get("/1").accept(MediaType.APPLICATION_JSON);
        String expected = "{\"id\":1,\"personAmount\":4,\"reservation\":null}";
        String expected2 = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";


        // when
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                                     .andReturn();
        // then
        JSONAssert.assertEquals(expected2, mvcResult.getResponse().getContentAsString(), false);
    }
}
