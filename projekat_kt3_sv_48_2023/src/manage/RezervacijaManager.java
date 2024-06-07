package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


import entity.DodatnaUsluga;
import entity.Gost;
import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.TipSobe;

public class RezervacijaManager {
	
	private String rezervacijaFile;
	private ArrayList<Rezervacija> rezervacije;
	private GostManager gm;
	private TipSobeManager tsm;
	private DodatnaUslugaManager dum;
	private SobaManager sm;
	private ArrayList<Rezervacija> filtriraneRezervacije;
	
	public RezervacijaManager(String rezervacijaFile) {
		this.rezervacijaFile = rezervacijaFile;
		this.rezervacije = new ArrayList<Rezervacija>();
		this.filtriraneRezervacije = new ArrayList<Rezervacija>();
		this.gm = new GostManager("data/gosti.csv");
		this.tsm = new TipSobeManager("data/tipoviSoba.csv");
		this.dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
		this.sm = new SobaManager("data/sobe.csv");
		gm.ucitajGoste();
		tsm.ucitajTipoveSoba();
		dum.ucitajDodatneUsluge();
		sm.ucitajSobe();
	}
	
	public ArrayList<Rezervacija> getRezervacije() {
		return rezervacije;
	}
	
	public void setRezervacijeNaCekanju(){
		ArrayList<Rezervacija> rezervacijeNaCekanju = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getStatusRezervacije() == StatusRezervacije.NA_ČEKANJU) {
				rezervacijeNaCekanju.add(r);
			}
		}
		this.filtriraneRezervacije = rezervacijeNaCekanju;
	}
	
	public void setFilter(ArrayList<TipSobe> tipoviSoba, ArrayList<DodatnaUsluga> dodatneUsluge) {
		ArrayList<Rezervacija> rezervacijeNaCekanju = this.getRezervacijeNaCekanju();
        ArrayList<Rezervacija> rezervacijeFiltrirane = new ArrayList<Rezervacija>();
        for (Rezervacija r : rezervacijeNaCekanju) {
            boolean tipSobeMatch = (tipoviSoba == null || tipoviSoba.size() == 0 || tipoviSoba.contains(r.getTipSobe()));
            boolean dodatneUslugeMatch = (dodatneUsluge == null || dodatneUsluge.size() == 0 || r.getDodatneUsluge().containsAll(dodatneUsluge));

            if (tipSobeMatch && dodatneUslugeMatch) {
                rezervacijeFiltrirane.add(r);
            }
        }
        this.filtriraneRezervacije = rezervacijeFiltrirane;
 
    }
	
    public ArrayList<Rezervacija> getRezervacijeNaCekanju() {
		return this.filtriraneRezervacije;
	}
    
    public ArrayList<Rezervacija> getPotvrdjeneRezervacije() {
    	ArrayList<Rezervacija> potvrdjeneRezervacije = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getStatusRezervacije() == StatusRezervacije.POTVRĐENA && r.getDatumPrijave().isEqual(LocalDate.now())) {
				potvrdjeneRezervacije.add(r);
			}
		}
		return potvrdjeneRezervacije;
    }
    
    public ArrayList<Rezervacija> getRezervacijeUToku() {
    	ArrayList<Rezervacija> rezervacijeUToku = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getStatusRezervacije() == StatusRezervacije.U_TOKU && r.getDatumOdjave().isEqual(LocalDate.now())) {
				rezervacijeUToku.add(r);
			}
		}
		return rezervacijeUToku;
    }
    
	public boolean ucitajRezervacije() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.rezervacijaFile));
			String linija = null;
			String regex = ",(?![^\\[]*\\])";
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(regex);
				Gost gost = gm.nadjiGosta(tokeni[1]);
				TipSobe tipSobe = tsm.nadjiTipSobe(tokeni[4]);
				ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<DodatnaUsluga>();
				for (String nazivUsluge : tokeni[6].substring(1, tokeni[6].length() - 1).split(", ")) {
					dodatneUsluge.add(dum.nadjiDodatnuUslugu(nazivUsluge));
				}
				Soba soba;
				if(tokeni[9].equals("nema")) {
					soba = null;
				}else {
					soba = sm.nadjiSobu(Integer.parseInt(tokeni[9]));
				}
				Rezervacija r = new Rezervacija(Integer.parseInt(tokeni[0]), gost, LocalDate.parse(tokeni[2]), LocalDate.parse(tokeni[3]), tipSobe, Integer.parseInt(tokeni[5]), dodatneUsluge, Double.parseDouble(tokeni[7]), StatusRezervacije.valueOf(tokeni[8]), soba);
				this.rezervacije.add(r);
				gost.getRezervacije().add(r);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajRezervacije() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.rezervacijaFile, false));
			for (Rezervacija r : rezervacije) {
				pw.println(r.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}
	
	public Rezervacija nadjiRezervaciju(int id) {
		for (Rezervacija r : rezervacije) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}
	
	public void dodajRezervacijuPoTipu(Gost gost, LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe, int brojLjudi,ArrayList<DodatnaUsluga> dodatneUsluge) {
		double cena = 0;
		int id;
		if(rezervacije.size() == 0) {
			id = 1;
		} else {
			id = rezervacije.get(rezervacije.size() - 1).getId() + 1;
		}
		Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi,dodatneUsluge, cena);
		r.setUkupnaCena(r.izracunajUkupnuCenu());
		rezervacije.add(r);
		gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
		gm.sacuvajGoste();
	}
	
	public void dodajRezervacijuPoBrojuLjudi(Gost gost, LocalDate datumPrijave, LocalDate datumOdjave, int brojLjudi, ArrayList<DodatnaUsluga> dodatneUsluge) {
		double cena = 0;
		int id;
		if(rezervacije.size() == 0) {
			id = 1;
		} else {
			id = rezervacije.get(rezervacije.size() - 1).getId() + 1;
		}
		for (TipSobe tipSobe : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave)) {
			if (tipSobe.getBrojOsoba() == brojLjudi) {
				Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi, dodatneUsluge, cena);
				r.setUkupnaCena(r.izracunajUkupnuCenu());
				rezervacije.add(r);
				gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
				gm.sacuvajGoste();
				return;
			}
		}
		for (TipSobe tipSobe : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave)) {
			if (tipSobe.getBrojOsoba() > brojLjudi) {
				Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi, dodatneUsluge, cena);
				r.setUkupnaCena(r.izracunajUkupnuCenu());
				rezervacije.add(r);
				gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
				gm.sacuvajGoste();
				return;
			}
		}
	}
	
	public void izmeniRezervaciju(int id, Gost gost, LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe,
			int brojLjudi, ArrayList<DodatnaUsluga> dodatneUsluge) {
		Rezervacija r = nadjiRezervaciju(id);
		if (r != null) {
			r.setRezervisao(gost);
			r.setDatumPrijave(datumPrijave);
			r.setDatumOdjave(datumOdjave);
			r.setTipSobe(tipSobe);
			r.setBrojLjudi(brojLjudi);
			r.setDodatneUsluge(dodatneUsluge);
			r.setUkupnaCena(r.izracunajUkupnuCenu());;
		} else {
			System.out.println("Rezervacija sa id " + id + " ne postoji u sistemu.");
		}
	}
	
	public void obrisiRezervaciju(int id) {
		Rezervacija r = nadjiRezervaciju(id);
		if (r != null) {
			rezervacije.remove(r);
		} else {
			System.out.println("Rezervacija sa id " + id + " ne postoji u sistemu.");
		}
	}
	
	private ArrayList<TipSobe> pronadjiSlobodneTipoveSoba(LocalDate pocetak, LocalDate kraj){
		ArrayList<TipSobe> slobodniTipovi = new ArrayList<TipSobe>();
		HashMap<String, Integer> brojSobaPoTipu = sm.getBrojSobaPoTipu();
		for (TipSobe ts : tsm.getTipoviSoba()) {
			boolean slobodan = true;
			for (Rezervacija r : rezervacije) {
				if (r.getDatumPrijave().isBefore(kraj) && r.getDatumOdjave().isAfter(pocetak) && r.getTipSobe().getNaziv().equals(ts.getNaziv()) && r.getStatusRezervacije() == StatusRezervacije.POTVRĐENA) {
					if (brojSobaPoTipu.get(ts.getNaziv()) - 1 == 0) {
						slobodan = false;
						break;
					} else {
						brojSobaPoTipu.put(ts.getNaziv(), brojSobaPoTipu.get(ts.getNaziv()) - 1);
					}	
				}
			}
			if (slobodan) {
				slobodniTipovi.add(ts);
			}
		}
		return slobodniTipovi;
	}
	
	public void ispisiSlobodneTipoveSoba(LocalDate pocetak, LocalDate kraj) {
		ArrayList<TipSobe> slobodniTipovi = pronadjiSlobodneTipoveSoba(pocetak, kraj);
		StringBuilder sb = new StringBuilder();
		for (TipSobe ts : slobodniTipovi) {
			sb.append(ts.getNaziv() + "\n");
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		System.out.println("Slobodni tipovi soba za period od " + pocetak.format(format) + " do " + kraj.format(format) + ":\n" + sb.toString());
	}
	
	public boolean isSlobodnaSobaZaPeriod(LocalDate pocetak, LocalDate kraj, TipSobe tipSobe) {
		ArrayList<TipSobe> slobodniTipovi = pronadjiSlobodneTipoveSoba(pocetak, kraj);
		if (slobodniTipovi.size() == 0 || slobodniTipovi.contains(tipSobe) == false) {
			return false;
		}
		return true;
	}
	
	private ArrayList<Rezervacija> dobaviRezervacijeZaGosta(Gost gost) {
		ArrayList<Rezervacija> rezervacijeZaGosta = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getRezervisao().equals(gost)) {
				rezervacijeZaGosta.add(r);
			}
		}
		return rezervacijeZaGosta;
	}
	
	public void prikaziRezervacijeZaGosta(Gost gost) {
		ArrayList<Rezervacija> rezervacijeZaGosta = dobaviRezervacijeZaGosta(gost);
		System.out.println("Rezervacije za gosta " + gost.getKorisnickoIme() + ":");
        for (Rezervacija r : rezervacijeZaGosta) {
            System.out.println(r);
        }
    }
	
	public void promeniStatusRezervacije(int id, StatusRezervacije status) {
		Rezervacija r = nadjiRezervaciju(id);
		if (r != null) {
			r.setStatusRezervacije(status);
		} else {
			System.out.println("Rezervacija sa id " + id + " ne postoji u sistemu.");
		}
	}
}
