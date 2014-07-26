package com.diegobonfim.guiaturisticorecife;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.diegobonfim.guiaturisticorecife.model.Brigde;
import com.diegobonfim.guiaturisticorecife.model.CityGim;
import com.diegobonfim.guiaturisticorecife.model.GovernmentBuilding;
import com.diegobonfim.guiaturisticorecife.model.Graveyard;
import com.diegobonfim.guiaturisticorecife.model.HealthNet;
import com.diegobonfim.guiaturisticorecife.model.HeritageTree;
import com.diegobonfim.guiaturisticorecife.model.Hotel;
import com.diegobonfim.guiaturisticorecife.model.MarketPlace;
import com.diegobonfim.guiaturisticorecife.model.Mart;
import com.diegobonfim.guiaturisticorecife.model.Museum;
import com.diegobonfim.guiaturisticorecife.model.ParkSquare;
import com.diegobonfim.guiaturisticorecife.model.ShoppingCenter;
import com.diegobonfim.guiaturisticorecife.model.Theater;
import com.diegobonfim.guiaturisticorecife.model.BikeRoad;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MapActivity extends Activity implements LocationListener{	
	public static final LatLng LAT_LNG_RECIFE = new LatLng(-8.0464492, -34.9324883);
	private static final Gson GSON = new Gson();
	
	private LocationManager locationManager;
	private List<Brigde> dbBridge;
	private List<CityGim> dbCityGim;
	private List<GovernmentBuilding> dbGovernmentBuildingGeo;
	private List<Graveyard> dbGraveyard;
	private List<HealthNet> dbHealthNetGeo;
	private List<HeritageTree> dbHeritageTree;
	private List<Hotel> dbHotel;
	private List<MarketPlace> dbMarketPlace;
	private List<Mart> dbMart;
	private List<Museum> dbMuseum;
	private List<ParkSquare> dbParkSquareGeo;
	private List<ShoppingCenter> dbShoppingCenter;
	private List<Theater> dbTheater;
	private List<BikeRoad> dbBikeRoad;
	private GoogleMap map;
	private Spinner categoryView;
	private String[] options = {
			"Pontes",
			"Academias",
			"Contruções Públicas",
			"Cemitérios",
			"Rede de Saúde",
			"Árvores Tombadas",
			"Hotéis",
			"Feira Livre",
			"Mercados",
			"Museus",
			"Praças e Parques",
			"Centro de Compras",
			"Ciclovias",
			"Teatro"
	};
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		dbBridge = (List<Brigde>) GSON.fromJson(loadJSON("bridge.json"), new TypeToken<List<Brigde>>() {}.getType());
		dbCityGim = (List<CityGim>) GSON.fromJson(loadJSON("city_gim.json"), new TypeToken<List<CityGim>>() {}.getType());
		dbGovernmentBuildingGeo = (List<GovernmentBuilding>) GSON.fromJson(loadJSON("government_building.json"), new TypeToken<List<GovernmentBuilding>>() {}.getType());
		dbGraveyard = (List<Graveyard>) GSON.fromJson(loadJSON("graveyard.json"), new TypeToken<List<Graveyard>>() {}.getType());
		dbHealthNetGeo = (List<HealthNet>) GSON.fromJson(loadJSON("healthnet.json"), new TypeToken<List<HealthNet>>() {}.getType());
		dbHeritageTree = (List<HeritageTree>) GSON.fromJson(loadJSON("heritage_tree.json"), new TypeToken<List<HeritageTree>>() {}.getType());
		dbHotel = (List<Hotel>) GSON.fromJson(loadJSON("hotel.json"), new TypeToken<List<Hotel>>() {}.getType());
		dbMarketPlace = (List<MarketPlace>) GSON.fromJson(loadJSON("marketplace.json"), new TypeToken<List<MarketPlace>>() {}.getType());
		dbMart = (List<Mart>) GSON.fromJson(loadJSON("mart.json"), new TypeToken<List<Mart>>() {}.getType());
		dbMuseum = (List<Museum>) GSON.fromJson(loadJSON("museum.json"), new TypeToken<List<Museum>>() {}.getType());
		dbParkSquareGeo = (List<ParkSquare>) GSON.fromJson(loadJSON("park_square.json"), new TypeToken<List<ParkSquare>>() {}.getType());
		dbShoppingCenter = (List<ShoppingCenter>) GSON.fromJson(loadJSON("shopping_center.json"), new TypeToken<List<ShoppingCenter>>() {}.getType());
		dbTheater = (List<Theater>) GSON.fromJson(loadJSON("theater.json"), new TypeToken<List<Theater>>() {}.getType());
		dbBikeRoad = (List<BikeRoad>) GSON.fromJson(loadJSON("BikeRoad.json"), new TypeToken<List<BikeRoad>>() {}.getType());
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(LAT_LNG_RECIFE, 11));
		categoryView = (Spinner) findViewById(R.id.category);
		categoryView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options));
		categoryView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String option = (String) categoryView.getItemAtPosition(position);
				map.clear();
				
				switch (option) {
					case "Pontes":
						for(Brigde x : dbBridge)
							addMarker(x.nome, x.descricao, x.latitude, x.longitude);
						break;
					case "Academias":
						for(CityGim x : dbCityGim)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Contruções Públicas":
						for(GovernmentBuilding x : dbGovernmentBuildingGeo)
							addPolygon(x.coordinates);
						break;
					case "Cemitérios":
						for(Graveyard x : dbGraveyard)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Rede de Saúde":
						for(HealthNet x : dbHealthNetGeo)
							addPolygon(x.coordinates);
						break;
					case "Árvores Tombadas":
						for(HeritageTree x : dbHeritageTree)
							addMarker(x.nomePopular, x.endereco, x.latitude, x.longitude);
						break;
					case "Hotéis":
						for(Hotel x : dbHotel)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Feira Livre":
						for(MarketPlace x : dbMarketPlace)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Mercados":
						for(Mart x : dbMart)
							addMarker(x.nome, x.descricao, x.latitude, x.longitude);
						break;
					case "Museus":
						for(Museum x : dbMuseum)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Praças e Parques":
						for(ParkSquare x : dbParkSquareGeo)
							addPolygon(x.coordinates);
						break;
					case "Centro de Compras":
						for(ShoppingCenter x : dbShoppingCenter)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Teatro":
						for(Theater x : dbTheater)
							addMarker(x.nome, x.endereco, x.latitude, x.longitude);
						break;
					case "Ciclovias":
						for(BikeRoad x : dbBikeRoad)
							addPolygonBike(x.coordinates);
						break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		});
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
		
		if(!isGpsEnabled())
            alertGpsNotEnabled();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void addMarker(String title, String snippet, float lat, float lng){
		try {
			map.addMarker(new MarkerOptions()
	            .title(title) 
	            .snippet(snippet)
	            .position(new LatLng(lat, lng)));
		} catch (Exception e) { }
	}
	
	private void addPolygon(List<List<Float>> latLng){ 
		List<LatLng> l = new ArrayList<LatLng>();
		for(List<Float> x : latLng)
			l.add(new LatLng(x.get(1), x.get(0)));
		
		PolylineOptions options = new PolylineOptions();
		options.addAll(l);
		options.color(Color.BLUE);
		options.width(5);
		map.addPolyline(options).setZIndex(8888);
		
	}
	
	private void addPolygonBike(List<List<Float>> latLng){ 
		List<LatLng> l = new ArrayList<LatLng>();
		for(List<Float> x : latLng)
			l.add(new LatLng(x.get(0), x.get(1)));
		
		PolylineOptions options = new PolylineOptions();
		options.addAll(l);
		options.color(Color.BLUE);
		options.width(5);
		map.addPolyline(options).setZIndex(8888);
		
	}
	
	public String loadJSON(String path) {
        String json = null;
        try {
            InputStream is = getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            return null;
        }
        return json;
    }
	
	private void alertGpsNotEnabled() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Seu GPS parece estar desativado, deseja ativa-lo?")
	           .setCancelable(false)
	           .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
	private boolean isGpsEnabled(){
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); 
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
