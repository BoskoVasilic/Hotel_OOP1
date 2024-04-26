package entity;

public class TipSobe {
	
	protected String naziv;
	protected int brojKreveta;
	protected int brojOsoba;

	public TipSobe(String naziv, int brojKreveta, int brojOsoba) {
		this.naziv = naziv;
		this.brojKreveta = brojKreveta;
		this.brojOsoba = brojOsoba;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public int getBrojOsoba() {
		return brojOsoba;
	}

	public void setBrojOsoba(int brojOsoba) {
		this.brojOsoba = brojOsoba;
	}
	
	

}
