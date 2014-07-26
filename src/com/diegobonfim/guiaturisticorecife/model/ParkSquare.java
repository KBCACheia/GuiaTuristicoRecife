package com.diegobonfim.guiaturisticorecife.model;

import java.io.Serializable;
import java.util.List;

public class ParkSquare implements Serializable {
	private static final long serialVersionUID = -208342416445758290L;

	public String nome;
	public String tipo;
	public String endereco;
	public String bairro;
	public List<List<Float>> coordinates;
}