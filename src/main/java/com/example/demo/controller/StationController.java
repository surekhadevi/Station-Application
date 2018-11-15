package com.example.demo.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Station;
import com.example.demo.repository.StationRepository;

@RestController
@RequestMapping("/station")
public class StationController {

	@Autowired
	StationRepository stationRepository;
	
	@GetMapping("/allstations")
	public List<Station> getAllStations(){
		return stationRepository.findAll();
	}
	
	@PostMapping("/addStation")
	public Station addStation(@RequestBody Station station) {
		return stationRepository.save(station);
	}
	
	@DeleteMapping("/deleteStation/{stationId}")
	public void removeStation(@PathVariable("stationId") String stationId) {
		stationRepository.deleteById(stationId);
	}
	
	@PutMapping("/updateStation/{stationId}")
	public Station updateStation(Station station) {
		return stationRepository.save(station);
	}
	
	@GetMapping("/getstationbyid/{stationId}")
	public Station getStationById(@PathVariable("stationId") String stationId) {
		return stationRepository.findById(stationId).get();
	}
	
	@GetMapping("/hdEnabledStations/{hdEnabled}")
	public List<Station> getHDEnabledStations(@PathVariable("hdEnabled") boolean hdEnabled){
		List<Station> stations = stationRepository.findAll();
		List<Station> HDenabledstations = new ArrayList<>();
		for(Station s: stations) {
			if(hdEnabled) {
				HDenabledstations.add(s);
			}
		}
		return HDenabledstations;
	}
}
