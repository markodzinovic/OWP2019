package model;

public class Karta {
	
	private int id;
	private Projekcije projekcija;
	private int sediste;
	private String datum;
	private Korisnik korisnik;
	
	public Karta() {
		super();
	}

	public Karta(int id, Projekcije projekcija, int sediste, String datum, Korisnik korisnik) {
		super();
		this.id = id;
		this.projekcija = projekcija;
		this.sediste = sediste;
		this.datum = datum;
		this.korisnik = korisnik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Projekcije getProjekcija() {
		return projekcija;
	}

	public void setProjekcija(Projekcije projekcija) {
		this.projekcija = projekcija;
	}

	public int getSediste() {
		return sediste;
	}

	public void setSediste(int sediste) {
		this.sediste = sediste;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	

}
