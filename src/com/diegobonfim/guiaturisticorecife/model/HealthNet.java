package com.diegobonfim.guiaturisticorecife.model;

import java.io.Serializable;
import java.util.List;

public class HealthNet implements Serializable {
	private static final long serialVersionUID = 2651910994746882818L;

	public String nome;
	public String endereco;
	public String tipoPatrimonio;
	public String tipoUnidadeSaude;
	public List<List<Float>> coordinates;
}
