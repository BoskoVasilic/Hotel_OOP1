package entity;

import java.time.LocalDate;
import java.util.HashMap;

public class Cenovnik {
	
	protected LocalDate pocetakVazenja;
	protected LocalDate krajVazenja;
	protected HashMap<String, Double> cene;
	
	public Cenovnik(LocalDate pocetakVazenja, LocalDate krajVazenja, HashMap<String, Double> cene) {
		this.pocetakVazenja = pocetakVazenja;
		this.krajVazenja = krajVazenja;
		this.cene = cene;
	}

	public LocalDate getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(LocalDate pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}

	public LocalDate getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(LocalDate krajVazenja) {
		this.krajVazenja = krajVazenja;
	}

	public HashMap<String, Double> getCene() {
		return cene;
	}

	public void setCene(HashMap<String, Double> cene) {
		this.cene = cene;
	}
	
	
}
