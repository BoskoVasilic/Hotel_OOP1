package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Rezervacija {
	
	protected int id;
	protected LocalDate datumPrijave;
	protected LocalDate datumOdjave;
	protected TipSobe tipSobe;
	protected int brojLjudi;
	protected ArrayList<DodatnaUsluga> dodatneUsluge;
	protected double ukupnaCena;
	
	public Rezervacija(int id, LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe, int brojLjudi,
			ArrayList<DodatnaUsluga> dodatneUsluge) {
		this.id = id;
		this.datumPrijave = datumPrijave;
		this.datumOdjave = datumOdjave;
		this.tipSobe = tipSobe;
		this.brojLjudi = brojLjudi;
		this.dodatneUsluge = dodatneUsluge;
		this.ukupnaCena = this.izracunajUkupnuCenu();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(LocalDate datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public LocalDate getDatumOdjave() {
		return datumOdjave;
	}

	public void setDatumOdjave(LocalDate datumOdjave) {
		this.datumOdjave = datumOdjave;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}

	public void setSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	
	public int getBrojLjudi() {
		return brojLjudi;
	}
	
	public void setBrojLjudi(int brojLjudi) {
		this.brojLjudi = brojLjudi;
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
	
	protected double izracunajUkupnuCenu() {
		double ukupnaCena = 0;
		
		return ukupnaCena;
	}

}
