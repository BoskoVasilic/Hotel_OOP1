package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Oprema;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;

public class SobaManager {
	private String sobaFile;
	private ArrayList<Soba> sobe;
	private TipSobeManager tsm;
	private OpremaManager om;
	
	public SobaManager(String sobaFile) {
		this.sobaFile = sobaFile;
		this.sobe = new ArrayList<Soba>();
		this.tsm = new TipSobeManager("data/tipoviSoba.csv");
		this.om = new OpremaManager("data/oprema.csv");
		tsm.ucitajTipoveSoba();
		om.ucitajOpremu();
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
				TipSobe tipSobe = tsm.nadjiTipSobe(tokeni[1]);
				ArrayList<Oprema> oprema = new ArrayList<Oprema>();
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
		if (nadjiSobu(brojSobe) != null) {
			System.out.println("Soba sa brojem " + brojSobe + " vec postoji u sistemu.");
			return;
		}
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
	
	public HashMap<String, Integer> getBrojSobaPoTipu() {
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
	
	public ArrayList<String> getSlobodneSobeTipa(TipSobe tipSobe, ArrayList<Oprema> zahtevanaOprema) {
		ArrayList<String> sobeTipa = new ArrayList<String>();
		for (Soba s : sobe) {
			if (s.getTipSobe().equals(tipSobe) && s.getStatusSobe() == StatusSobe.SLOBODNA && (zahtevanaOprema.size() == 0 || s.getOpremljenostSobe().containsAll(zahtevanaOprema))) {
				sobeTipa.add(String.valueOf(s.getBrojSobe()));
			}
		}
		return sobeTipa;
	}
	
}
