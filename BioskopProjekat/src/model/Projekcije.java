package model;

public class Projekcije {

	private int id;
	private Film nazivFilma;
	private TipoviProjekcije tipProjekcije;
	private Sala sala;
	private String datum;
	private double cena;
	private Korisnik administrator;
	private boolean obrisan;
	
	public Projekcije() {
		super();
	}

	public Projekcije(int id, Film nazivFilma, TipoviProjekcije tipProjekcije, Sala sala, String datum, double cena,
			Korisnik administrator, boolean obrisan) {
		super();
		this.id = id;
		this.nazivFilma = nazivFilma;
		this.tipProjekcije = tipProjekcije;
		this.sala = sala;
		this.datum = datum;
		this.cena = cena;
		this.administrator = administrator;
		this.obrisan = obrisan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Film getNazivFilma() {
		return nazivFilma;
	}

	public void setNazivFilma(Film nazivFilma) {
		this.nazivFilma = nazivFilma;
	}

	public TipoviProjekcije getTipProjekcije() {
		return tipProjekcije;
	}

	public void setTipProjekcije(TipoviProjekcije tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Korisnik getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Korisnik administrator) {
		this.administrator = administrator;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	
}
