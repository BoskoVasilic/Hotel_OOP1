package entity;

public class TipSobe {
	
	protected String naziv;
	protected int brojKreveta;
	protected int brojOsoba;
	
	public TipSobe() {
		this.naziv = "";
		this.brojKreveta = 0;
		this.brojOsoba = 0;
	}

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
	
	@Override
	public String toString() {
		return "Naziv: " + naziv + "\nBroj kreveta: " + brojKreveta + "\nBroj osoba: " + brojOsoba;
	}
	
	public String toFile() {
		return this.naziv + "," + this.brojKreveta + "," + this.brojOsoba;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TipSobe) {
			TipSobe ts = (TipSobe) obj;
			if (this.naziv.equals(ts.getNaziv()) && this.brojKreveta == ts.getBrojKreveta()
					&& this.brojOsoba == ts.getBrojOsoba()) {
				return true;
			}
		}
		return false;
	}
	

}
