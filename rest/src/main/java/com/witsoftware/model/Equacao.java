package com.witsoftware.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.ToString;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Equacao.class)
@Data
@ToString
public class Equacao {

	private Double a;
	private String operador;
	private Double b;

}
