package entity;

public class DodatnaUsluga {
	
	protected String naziv;
	
	public DodatnaUsluga(String naziv) {
		this.naziv = naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public String toFile() {
		return this.naziv;
	}
}
