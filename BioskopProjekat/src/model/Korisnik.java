package model;

import java.util.Date;

public class Korisnik {

	public enum Uloga {ADMIN, KORISNIK};
	
	private String korisnickoIme;
	private String lozinka;
	private String datumRegistracije;
	private Uloga uloga;
	
	public Korisnik() {
		super();
	}

	public Korisnik(String korisnickoIme, String lozinka, String datumRegistracije, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.datumRegistracije = datumRegistracije;
		this.uloga = uloga;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(String datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	
	
}
