package model;

import java.util.ArrayList;

public class Sala {

	private int id;
	private String naziv;
	private ArrayList<TipoviProjekcije> tipoviP;
	
	
	public Sala() {
		super();
	}


	public Sala(int id, String naziv, ArrayList<TipoviProjekcije> tipoviP) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tipoviP = tipoviP;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public ArrayList<TipoviProjekcije> getTipoviP() {
		return tipoviP;
	}


	public void setTipoviP(ArrayList<TipoviProjekcije> tipoviP) {
		this.tipoviP = tipoviP;
	}
	
	
	
	
}
