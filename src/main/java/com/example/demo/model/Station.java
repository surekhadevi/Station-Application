package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Station {

	@Id
	String stationId;
	String name;
	Boolean hdEnabled;
	String callSign;

}
