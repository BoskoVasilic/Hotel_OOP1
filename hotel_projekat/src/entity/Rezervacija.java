package entity;

import java.util.ArrayList;

public class Rezervacija {
	
	protected int id;
	protected String datumPrijave;
	protected String datumOdjave;
	protected ArrayList<Soba> sobe;
	protected ArrayList<DodatnaUsluga> dodatneUsluge;
	protected double ukupnaCena;
	
	public Rezervacija(int id, String datumPrijave, String datumOdjave, ArrayList<Soba> sobe,
			ArrayList<DodatnaUsluga> dodatneUsluge) {
		this.id = id;
		this.datumPrijave = datumPrijave;
		this.datumOdjave = datumOdjave;
		this.sobe = sobe;
		this.dodatneUsluge = dodatneUsluge;
		this.ukupnaCena = this.izracunajUkupnuCenu();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(String datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public String getDatumOdjave() {
		return datumOdjave;
	}

	public void setDatumOdjave(String datumOdjave) {
		this.datumOdjave = datumOdjave;
	}

	public ArrayList<Soba> getSobe() {
		return sobe;
	}

	public void setSobe(ArrayList<Soba> sobe) {
		this.sobe = sobe;
	}

	public ArrayList<DodatnaUsluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(ArrayList<DodatnaUsluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}
	
	public double izracunajUkupnuCenu() {
		double ukupnaCena = 0;
		for (Soba soba : sobe) {
			ukupnaCena += soba.getCena();
		}
		for (DodatnaUsluga dodatnaUsluga : dodatneUsluge) {
			ukupnaCena += dodatnaUsluga.getCena();
		}
		return ukupnaCena;
	}

}
