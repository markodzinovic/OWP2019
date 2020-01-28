package model;

public class Sedista {
	
	private int redniBroj;
	private Sala sala;
	
	public Sedista() {
		super();
	}

	public Sedista(int redniBroj, Sala sala) {
		super();
		this.redniBroj = redniBroj;
		this.sala = sala;
	}

	public int getRedniBroj() {
		return redniBroj;
	}

	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	

}
