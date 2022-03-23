package com.witsoftware.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Equation implements Serializable {

	private Double firstOperand;
	private String operador;
	private Double secondOperand;

}
