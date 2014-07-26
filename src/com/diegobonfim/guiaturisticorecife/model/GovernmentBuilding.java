package com.diegobonfim.guiaturisticorecife.model;

import java.io.Serializable;
import java.util.List;

public class GovernmentBuilding implements Serializable {
	private static final long serialVersionUID = 793087854600125441L;

	public String nome;
	public List<List<Float>> coordinates;
}
