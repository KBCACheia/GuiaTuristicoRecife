package com.diegobonfim.guiaturisticorecife.model;

import java.io.Serializable;
import java.util.List;

public class BikeRoad implements Serializable {
	private static final long serialVersionUID = 7949623377925122499L;
	
	public String type;
	public String name;
	public String description;
	public List<List<Float>> coordinates;
}
