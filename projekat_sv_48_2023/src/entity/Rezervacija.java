package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	protected StatusRezervacije statusRezervacije;
	protected Soba dodeljenaSoba;
	protected ArrayList<Oprema> zahtevanaOprema;
	protected Sobarica sobuOcistila;
	
	public Rezervacija(int id, Gost rezervisao,LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe, int brojLjudi,
			ArrayList<DodatnaUsluga> dodatneUsluge, double ukupnaCena, ArrayList<Oprema> zahtevanaOprema) {
		this.id = id;
		this.rezervisao = rezervisao;
		this.datumPrijave = datumPrijave;
		this.datumOdjave = datumOdjave;
		this.tipSobe = tipSobe;
		this.brojLjudi = brojLjudi;
		this.dodatneUsluge = dodatneUsluge;
		this.ukupnaCena = ukupnaCena;
		this.statusRezervacije = StatusRezervacije.NA_ČEKANJU;
		this.dodeljenaSoba = null;
		this.zahtevanaOprema = zahtevanaOprema;
		this.sobuOcistila = null;
	}
	
	public Rezervacija(int id, Gost rezervisao, LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe,
			int brojLjudi, ArrayList<DodatnaUsluga> dodatneUsluge, double ukupnaCena, StatusRezervacije statusRezervacije, Soba dodeljenaSoba, ArrayList<Oprema> zahtevanaOprema, Sobarica sobuOcistila) {
		this.id = id;
		this.rezervisao = rezervisao;
		this.datumPrijave = datumPrijave;
		this.datumOdjave = datumOdjave;
		this.tipSobe = tipSobe;
		this.brojLjudi = brojLjudi;
		this.dodatneUsluge = dodatneUsluge;
		this.ukupnaCena = ukupnaCena;
		this.statusRezervacije = statusRezervacije;
		this.dodeljenaSoba = dodeljenaSoba;
		this.zahtevanaOprema = zahtevanaOprema;
		this.sobuOcistila = sobuOcistila;
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

	public void setTipSobe(TipSobe tipSobe) {
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
	
	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}
	
	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}
	
	public Soba getDodeljenaSoba() {
		return dodeljenaSoba;
	}
	
	public void setDodeljenaSoba(Soba dodeljenaSoba) {
		this.dodeljenaSoba = dodeljenaSoba;
	}
	
	public ArrayList<Oprema> getZahtevanaOprema() {
		return zahtevanaOprema;
	}
	
	public void setZahtevanaOprema(ArrayList<Oprema> zahtevanaOprema) {
		this.zahtevanaOprema = zahtevanaOprema;
	}
	
	public Sobarica getSobuOcistila() {
		return sobuOcistila;
	}
	
	public void setSobuOcistila(Sobarica sobuOcistila) {
		this.sobuOcistila = sobuOcistila;
	}
	
	public double izracunajUkupnuCenu() {
		double ukupnaCena = 0;
		CenovnikManager cm = new CenovnikManager("data/cenovnik.csv");
		cm.ucitajCenovnike();
		for (LocalDate datum = datumPrijave; datum.isBefore(datumOdjave); datum = datum.plusDays(1)) {
			Cenovnik cenovnik = cm.nadjiCenovnikZaDatum(datum);
			ukupnaCena += cenovnik.dobaviCenu("TipoviSoba", tipSobe.getNaziv());
			ukupnaCena += dodatneUsluge.stream().mapToDouble(du -> cenovnik.dobaviCenu("DodatneUsluge", du.getNaziv())).sum();
		}
		return ukupnaCena;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		return "Datum prijave: " + datumPrijave.format(format) + "\nDatum odjave: " + datumOdjave.format(format) + "\nTip sobe: " + tipSobe.getNaziv() + "\nBroj ljudi: " + brojLjudi + "\nDodatne usluge: " + dodatneUsluge + "\nUkupna cena: " + ukupnaCena + "\nStatus rezervacije: " + statusRezervacije + "\nDodeljena soba: " + (dodeljenaSoba != null ? dodeljenaSoba.getBrojSobe() : "jos nije dodeljena") + "\nZahtevana oprema: " + zahtevanaOprema + "\nSobu ocistila: " + (sobuOcistila != null ? sobuOcistila.getKorisnickoIme() : "jos nije ociscena") + "\n";

	}
	
	public String toFile() {
        return id + "," + rezervisao.getKorisnickoIme() + "," + datumPrijave + "," + datumOdjave + "," + tipSobe.getNaziv() + "," + brojLjudi + "," + dodatneUsluge + "," + ukupnaCena + "," + statusRezervacije + "," + (dodeljenaSoba != null ? dodeljenaSoba.getBrojSobe() : "nema") + "," + zahtevanaOprema + "," + (sobuOcistila != null ? sobuOcistila.getKorisnickoIme() : "nema");
    }
	
}
