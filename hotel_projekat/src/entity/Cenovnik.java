package entity;

import java.time.LocalDate;
import java.util.HashMap;

public class Cenovnik {
	
	protected LocalDate pocetakVazenja;
	protected LocalDate krajVazenja;
	protected HashMap<String, HashMap<String, Double>> cene;
	
	public Cenovnik(LocalDate pocetakVazenja, LocalDate krajVazenja, HashMap<String, HashMap<String, Double>> cene) {
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

	public HashMap<String, HashMap<String, Double>> getCene() {
		return cene;
	}

	public void setCene(HashMap<String, HashMap<String, Double>> cene) {
		this.cene = cene;
	}
	
	public double dobaviCenu(String grupa, String stavka) {
		if (this.cene.containsKey(grupa)) {
			if (this.cene.get(grupa).containsKey(stavka)) {
				return this.cene.get(grupa).get(stavka);
			}
		}
		return -1;
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.cene.forEach((k, v) -> {
			sb.append(k + ": ");
			v.forEach((k1, v1) -> {
				sb.append(k1 + "=" + v1 + ", ");
			});
		});
		return "Pocetak vazenja " + pocetakVazenja + "\nKraj vazenja: " + krajVazenja + "\n" + sb.toString().substring(0, sb.length() - 1);
	}
	
	public String toFile() {
		StringBuilder sb = new StringBuilder();
		this.cene.forEach((k, v) -> {
			sb.append(",");
			sb.append(k + ",");
			v.forEach((k1, v1) -> {
				sb.append(k1 + "=" + v1 + ",");
			});
		});
		return this.pocetakVazenja + "," + this.krajVazenja + sb.toString().substring(0, sb.length() - 1);
	}
	
}
