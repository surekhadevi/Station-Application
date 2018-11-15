package com.example.demo.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.controller.StationController;
import com.example.demo.model.Station;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=StationController.class,secure = false)
public class stationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StationController stationController;
	
	@Test
	public void testGetAllStations() throws Exception {

		Station mockStation1 = new Station();
		mockStation1.setStationId("1");
		mockStation1.setName("Channel1");
		mockStation1.setHdEnabled(true);
		mockStation1.setCallSign("KA");
		
		Station mockStation2 = new Station();
		mockStation2.setStationId("2");
		mockStation2.setName("Channel2");
		mockStation2.setHdEnabled(false);
		mockStation2.setCallSign("KC");
		
		List<Station> stationList = new ArrayList<>();
		stationList.add(mockStation1);
		stationList.add(mockStation2);
		
		Mockito.when(stationController.getAllStations()).thenReturn(stationList);
		
		String URI = "/station/allstations";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(stationList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testAddStation() throws Exception {
		
		Station mockStation = new Station();
		mockStation.setStationId("1");
		mockStation.setName("Channel1");
		mockStation.setHdEnabled(true);
		mockStation.setCallSign("KA");
		
		String inputInJson = this.mapToJson(mockStation);
		
		String URI = "/station/addStation";
		
		Mockito.when(stationController.addStation(Mockito.any(Station.class))).thenReturn(mockStation);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testGetHDEnabledStations() throws Exception {
		Station mockStation1 = new Station();
		mockStation1.setStationId("1");
		mockStation1.setName("Channel1");
		mockStation1.setHdEnabled(true);
		mockStation1.setCallSign("KA");
		
		Station mockStation2 = new Station();
		mockStation2.setStationId("2");
		mockStation2.setName("Channel2");
		mockStation2.setHdEnabled(false);
		mockStation2.setCallSign("KC");
		
		List<Station> stationList = new ArrayList<>();
		stationList.add(mockStation1);
		stationList.add(mockStation2);
		
		String expectedJson = this.mapToJson(stationList);
		
		Mockito.when(stationController.getHDEnabledStations(Mockito.eq(true))).thenReturn(stationList);
		
		String URI = "/station/hdEnabledStations/true";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	
	}
	
	@Test
	public void testGetStationById() throws Exception {
		Station mockStation = new Station();
		mockStation.setStationId("1");
		mockStation.setName("Channel1");
		mockStation.setHdEnabled(true);
		mockStation.setCallSign("KA");
		
		Mockito.when(stationController.getStationById(Mockito.anyString())).thenReturn(mockStation);
		
		String URI = "/station/getstationbyid/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockStation);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
