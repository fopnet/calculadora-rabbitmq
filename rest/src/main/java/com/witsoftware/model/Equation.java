package com.witsoftware.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.ToString;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id", scope = Equation.class)
@Data
@ToString
public class Equation implements Serializable {

	private Double firstOperand;
	private String operador;
	private Double secondOperand;

	public String getOperatorOne() {
		return this.firstOperand < 0 ? "-" : "+";
	}

	public String getOperatorTwo() {
		return this.operador;
	}

}
