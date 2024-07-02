package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


import entity.DodatnaUsluga;
import entity.Gost;
import entity.Oprema;
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
	private OpremaManager om;
	
	private RezervacijaManager(String rezervacijaFile) {
		this.rezervacijaFile = rezervacijaFile;
		this.rezervacije = new ArrayList<Rezervacija>();
		this.filtriraneRezervacije = new ArrayList<Rezervacija>();
		this.gm = GostManager.getInstance();
		this.tsm = new TipSobeManager("data/tipoviSoba.csv");
		this.dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
		this.sm = new SobaManager("data/sobe.csv");
		this.om = new OpremaManager("data/oprema.csv");
		gm.ucitajGoste();
		tsm.ucitajTipoveSoba();
		dum.ucitajDodatneUsluge();
		sm.ucitajSobe();
		om.ucitajOpremu();
	}
	
	private static RezervacijaManager instance;
	
	public static RezervacijaManager getInstance() {
		if (instance == null) {
			instance = new RezervacijaManager("data/rezervacije.csv");
			instance.ucitajRezervacije();
		}
		return instance;
	}
	
	public ArrayList<Rezervacija> getRezervacije() {
		return rezervacije;
	}
	
	public ArrayList<Rezervacija> getRezervacijeZaGosta() {
		ArrayList<Rezervacija> rezervacijeZaGosta = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getRezervisao().equals(gm.getUlogovaniGost())) {
				rezervacijeZaGosta.add(r);
			}
		}
		return rezervacijeZaGosta;
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
			if (r.getStatusRezervacije() == StatusRezervacije.U_TOKU) {
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
				ArrayList<Oprema> zahtevanaOprema = new ArrayList<Oprema>();
				for (String nazivOpreme : tokeni[10].substring(1, tokeni[10].length() - 1).split(", ")) {
					zahtevanaOprema.add(om.nadjiOpremu(nazivOpreme.trim()));
				}
				Rezervacija r = new Rezervacija(Integer.parseInt(tokeni[0]), gost, LocalDate.parse(tokeni[2]), LocalDate.parse(tokeni[3]), tipSobe, Integer.parseInt(tokeni[5]), dodatneUsluge, Double.parseDouble(tokeni[7]), StatusRezervacije.valueOf(tokeni[8]), soba, zahtevanaOprema);
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
	
	public void dodajRezervacijuPoTipu(Gost gost, LocalDate datumPrijave, LocalDate datumOdjave, TipSobe tipSobe, int brojLjudi,ArrayList<DodatnaUsluga> dodatneUsluge, ArrayList<Oprema> zahtevanaOprema) {
		double cena = 0;
		int id;
		if(rezervacije.size() == 0) {
			id = 1;
		} else {
			id = rezervacije.get(rezervacije.size() - 1).getId() + 1;
		}
		Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi,dodatneUsluge, cena, zahtevanaOprema);
		r.setUkupnaCena(r.izracunajUkupnuCenu());
		rezervacije.add(r);
		gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
		gm.sacuvajGoste();
	}
	
	public boolean dodajRezervacijuPoBrojuLjudi(Gost gost, LocalDate datumPrijave, LocalDate datumOdjave, int brojLjudi, ArrayList<DodatnaUsluga> dodatneUsluge, ArrayList<Oprema> zahtevanaOprema) {
		double cena = 0;
		int id;
		if(rezervacije.size() == 0) {
			id = 1;
		} else {
			id = rezervacije.get(rezervacije.size() - 1).getId() + 1;
		}
		for (String tipSobeNaziv : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave).keySet()) {
			TipSobe tipSobe = tsm.nadjiTipSobe(tipSobeNaziv);
			for(ArrayList<Oprema> oprema : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave).get(tipSobe.getNaziv())) {
				if (tipSobe.getBrojOsoba() == brojLjudi && (zahtevanaOprema.size() == 0 || oprema.containsAll(zahtevanaOprema))) {
					Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi, dodatneUsluge, cena, zahtevanaOprema);
					r.setUkupnaCena(r.izracunajUkupnuCenu());
					rezervacije.add(r);
					gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
					gm.sacuvajGoste();
					return true;
				}
			}
		}
		for (String tipSobeNaziv : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave).keySet()) {
			TipSobe tipSobe = tsm.nadjiTipSobe(tipSobeNaziv);
			for(ArrayList<Oprema> oprema : pronadjiSlobodneTipoveSoba(datumPrijave, datumOdjave).get(tipSobe.getNaziv())) {
				if (tipSobe.getBrojOsoba() > brojLjudi && (zahtevanaOprema.size() == 0 || oprema.containsAll(zahtevanaOprema))) {
					Rezervacija r = new Rezervacija(id, gost, datumPrijave, datumOdjave, tipSobe, brojLjudi, dodatneUsluge, cena, zahtevanaOprema);
					r.setUkupnaCena(r.izracunajUkupnuCenu());
					rezervacije.add(r);
					gm.nadjiGosta(gost.getKorisnickoIme()).getRezervacije().add(r);
					gm.sacuvajGoste();
					return true;
				}
			}	
		}
		return false;
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
	
	private HashMap<String, ArrayList<ArrayList<Oprema>>> pronadjiSlobodneTipoveSoba(LocalDate pocetak, LocalDate kraj){
		HashMap<String, ArrayList<ArrayList<Oprema>>> slobodniTipovi = new HashMap<String, ArrayList<ArrayList<Oprema>>>();
		HashMap<String, Integer> brojSobaPoTipu = sm.getBrojSobaPoTipu();
		HashMap<String, ArrayList<ArrayList<Oprema>>> opremaPoTipu = sm.getOpremaPoTipuSobe();
		for (TipSobe ts : tsm.getTipoviSoba()) {
			boolean slobodan = true;
			for (Rezervacija r : rezervacije) {
				for (ArrayList<Oprema> oprema : opremaPoTipu.get(ts.getNaziv())) {
					if (r.getDatumPrijave().isBefore(kraj) && r.getDatumOdjave().isAfter(pocetak) && r.getTipSobe().getNaziv().equals(ts.getNaziv()) && (r.getStatusRezervacije() == StatusRezervacije.POTVRĐENA || r.getStatusRezervacije() == StatusRezervacije.U_TOKU)
							&& (r.getZahtevanaOprema().size() == 0 || oprema.containsAll(r.getZahtevanaOprema()))) {
						if (brojSobaPoTipu.get(ts.getNaziv()) - 1 == 0) {
							slobodan = false;
							break;
						} else {
							brojSobaPoTipu.put(ts.getNaziv(), brojSobaPoTipu.get(ts.getNaziv()) - 1);
                            opremaPoTipu.get(ts.getNaziv()).remove(oprema);
                            break;
						}	
					}
				}
				if (slobodan == false) {
					break;
				}
			}
			if (slobodan) {
				slobodniTipovi.put(ts.getNaziv(), opremaPoTipu.get(ts.getNaziv()));
			}
		}
		return slobodniTipovi;
	}
	
	/*public void ispisiSlobodneTipoveSoba(LocalDate pocetak, LocalDate kraj) {
		ArrayList<TipSobe> slobodniTipovi = pronadjiSlobodneTipoveSoba(pocetak, kraj);
		StringBuilder sb = new StringBuilder();
		for (TipSobe ts : slobodniTipovi) {
			sb.append(ts.getNaziv() + "\n");
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		System.out.println("Slobodni tipovi soba za period od " + pocetak.format(format) + " do " + kraj.format(format) + ":\n" + sb.toString());
	}*/
	
	public boolean isSlobodnaSobaZaPeriod(LocalDate pocetak, LocalDate kraj, TipSobe tipSobe) {
		HashMap<String, ArrayList<ArrayList<Oprema>>> slobodniTipovi = pronadjiSlobodneTipoveSoba(pocetak, kraj);
		return slobodniTipovi.containsKey(tipSobe.getNaziv());
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
	
	public ArrayList<TipSobe> postojeSobeKojeZadovoljavajuUslove(ArrayList<Oprema> oprema, LocalDate pocetak, LocalDate kraj) {
		HashMap<String, ArrayList<ArrayList<Oprema>>> tipoviSoba = new HashMap<String, ArrayList<ArrayList<Oprema>>>();
		tipoviSoba = this.pronadjiSlobodneTipoveSoba(pocetak, kraj);
		ArrayList<TipSobe> tipoviSobaKojeZadovoljavajuUslove = new ArrayList<TipSobe>();
		for (Soba s : sm.getSobe()) {
			for(ArrayList<Oprema> dostupnaOprema : tipoviSoba.get(s.getTipSobe().getNaziv())){
				if (tipoviSoba.containsKey(s.getTipSobe().getNaziv()) && (oprema.size() == 0 || (s.getOpremljenostSobe().containsAll(oprema) && dostupnaOprema.containsAll(oprema))) && tipoviSobaKojeZadovoljavajuUslove.contains(s.getTipSobe()) == false) {
					tipoviSobaKojeZadovoljavajuUslove.add(s.getTipSobe());
					break;
				}
			}
			
		}
		return tipoviSobaKojeZadovoljavajuUslove;
	}
	
	public int izracunajUkupanTrosakZaGosta() {
		int ukupanTrosak = 0;
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		rezervacije = dobaviRezervacijeZaGosta(gm.getUlogovaniGost());
        for (Rezervacija r : rezervacije) {
            ukupanTrosak += r.getUkupnaCena();
        }
        return ukupanTrosak;
    }
	
	public double getPrihodi(LocalDate pocetak, LocalDate kraj) {
		double prihodi = 0;
		for (Rezervacija r : rezervacije) {
			if (r.getDatumPrijave().isAfter(pocetak) && r.getDatumOdjave().isBefore(kraj)) {
				prihodi += r.getUkupnaCena();
			}
		}
		return prihodi;
	}
	
	public int getBrojPotvrdjenihRezervacija(LocalDate pocetak, LocalDate kraj) {
		int brojPotvrdjenihRezervacija = 0;
        for (Rezervacija r : rezervacije) {
            if (r.getDatumPrijave().isAfter(pocetak) && r.getDatumOdjave().isBefore(kraj) && r.getStatusRezervacije() != StatusRezervacije.NA_ČEKANJU && r.getStatusRezervacije() != StatusRezervacije.OTKAZANA && r.getStatusRezervacije() != StatusRezervacije.ODBIJENA) {
                brojPotvrdjenihRezervacija++;
            }
        }
        return brojPotvrdjenihRezervacija;
	}
}
