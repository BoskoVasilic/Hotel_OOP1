package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Oprema;
import entity.Rezervacija;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;

public class SobaManager {
	private String sobaFile;
	private ArrayList<Soba> sobe;
	private TipSobeManager tsm;
	private OpremaManager om;
	private RezervacijaManager rm;
	
	public SobaManager(String sobaFile) {
		this.sobaFile = sobaFile;
		this.sobe = new ArrayList<Soba>();
		this.tsm = new TipSobeManager("data/tipoviSoba.csv");
		this.om = new OpremaManager("data/oprema.csv");
		this.rm = new RezervacijaManager("data/rezervacije.csv");
	}
	
	public ArrayList<Soba> getSobe() {
		return sobe;
	}
	
	public boolean ucitajSobe() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.sobaFile));
			String linija = null;
			String regex = ",(?![^\\[]*\\])";
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(regex);
				tsm.ucitajTipoveSoba();
				TipSobe tipSobe = tsm.nadjiTipSobe(tokeni[1]);
				ArrayList<Oprema> oprema = new ArrayList<Oprema>();
				om.ucitajOpremu();
				for (String nazivOpreme : tokeni[3].substring(1, tokeni[3].length() - 1).split(", ")) {
					oprema.add(om.nadjiOpremu(nazivOpreme.trim()));
				}
				Soba s = new Soba(Integer.parseInt(tokeni[0]), tipSobe,StatusSobe.valueOf(tokeni[2]), oprema,Boolean.parseBoolean(tokeni[4]));
				this.sobe.add(s);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajSobe() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.sobaFile, false));
			for (Soba s : sobe) {
				pw.println(s.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Soba nadjiSobu(int brojSobe) {
		for (Soba s : sobe) {
			if (s.getBrojSobe() == brojSobe) {
				return s;
			}
		}
		return null;
	}
	
	public void dodajSobu(int brojSobe, TipSobe tipSobe, StatusSobe statusSobe, ArrayList<Oprema> opremljenostSobe, boolean pusacka) {
        Soba s = new Soba(brojSobe, tipSobe, statusSobe, opremljenostSobe, pusacka);
        sobe.add(s);
    }
	
	public void izmeniSobu(int brojSobe, TipSobe tipSobe, StatusSobe statusSobe, ArrayList<Oprema> opremljenostSobe,
			boolean pusacka) {
		Soba soba = nadjiSobu(brojSobe);
		if (soba != null) {
			soba.setTipSobe(tipSobe);
			soba.setStatusSobe(statusSobe);
			soba.setOpremljenostSobe(opremljenostSobe);
			soba.setPusackaSoba(pusacka);
		} else {
			System.out.println("Soba sa brojem " + brojSobe + " ne postoji u sistemu.");
		}
	}
	
	public void obrisiSobu(int brojSobe) {
		Soba soba = nadjiSobu(brojSobe);
		if (soba != null) {
			sobe.remove(soba);
		} else {
			System.out.println("Soba sa brojem " + brojSobe + " ne postoji u sistemu.");
		}
	}
	
	private HashMap<String, Integer> getBrojSobaPoTipu() {
		HashMap<String, Integer> brojSobaPoTipu = new HashMap<String, Integer>();
		for (Soba s : sobe) {
			if (brojSobaPoTipu.containsKey(s.getTipSobe().getNaziv())) {
				brojSobaPoTipu.put(s.getTipSobe().getNaziv(), brojSobaPoTipu.get(s.getTipSobe().getNaziv()) + 1);
			} else {
				brojSobaPoTipu.put(s.getTipSobe().getNaziv(), 1);
			}
		}
		return brojSobaPoTipu;
	}
	
	private ArrayList<TipSobe> pronadjiSlobodneTipoveSoba(LocalDate pocetak, LocalDate kraj){
		ArrayList<TipSobe> slobodniTipovi = new ArrayList<TipSobe>();
		rm.ucitajRezervacije();
		tsm.ucitajTipoveSoba();
		HashMap<String, Integer> brojSobaPoTipu = getBrojSobaPoTipu();
		for (TipSobe ts : tsm.getTipoviSoba()) {
			boolean slobodan = true;
			for (Rezervacija r : rm.getRezervacije()) {
				if (r.getDatumPrijave().isBefore(kraj) && r.getDatumOdjave().isAfter(pocetak) && r.getTipSobe().getNaziv().equals(ts.getNaziv())) {
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
		System.out.println(sb.toString());
	}
}
