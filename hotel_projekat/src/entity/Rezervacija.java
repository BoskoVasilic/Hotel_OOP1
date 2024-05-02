package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import manage.CenovnikManager;

public class Rezervacija {
	
	protected int id;
	protected Gost rezervisao;
	protected LocalDate datumPrijave;
	protected LocalDate datumOdjave;
	protected TipSobe tipSobe;
	protected int brojLjudi;
	protected ArrayList<DodatnaUsluga> dodatneUsluge;
	protected double ukupnaCena;
	
	public Rezervacija(int id, Gost rezervisao,LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe, int brojLjudi,
			ArrayList<DodatnaUsluga> dodatneUsluge, double ukupnaCena) {
		this.id = id;
		this.rezervisao = rezervisao;
		this.datumPrijave = datumPrijave;
		this.datumOdjave = datumOdjave;
		this.tipSobe = tipSobe;
		this.brojLjudi = brojLjudi;
		this.dodatneUsluge = dodatneUsluge;
		this.ukupnaCena = ukupnaCena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Gost getRezervisao() {
		return rezervisao;
	}
	
	public void setRezervisao(Gost rezervisao) {
		this.rezervisao = rezervisao;
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
		CenovnikManager cm = new CenovnikManager("data/cenovnik.csv");
		for (LocalDate datum = datumPrijave; datum.isBefore(datumOdjave); datum = datum.plusDays(1)) {
			cm.ucitajCenovnike();
			Cenovnik cenovnik = cm.nadjiCenovnikZaDatum(datum);
			ukupnaCena += cenovnik.dobaviCenu("TipoviSoba", tipSobe.getNaziv());
			ukupnaCena += dodatneUsluge.stream().mapToDouble(du -> cenovnik.dobaviCenu("DodatneUsluge", du.getNaziv())).sum();
		}
		return ukupnaCena;
	}
	
	public String toFile() {
        return id + "," + rezervisao.getKorisnickoIme() + "," + datumPrijave + "," + datumOdjave + "," + tipSobe.getNaziv() + "," + brojLjudi + "," + dodatneUsluge + "," + ukupnaCena;
    }
	
}
