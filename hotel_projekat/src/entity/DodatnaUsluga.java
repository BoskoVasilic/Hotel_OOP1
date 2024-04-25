package entity;

public class DodatnaUsluga {
	
	protected String naziv;
	protected double cena;
	
	public DodatnaUsluga(String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	

}
