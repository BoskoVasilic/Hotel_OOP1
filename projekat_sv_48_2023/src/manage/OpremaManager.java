package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entity.Oprema;

public class OpremaManager {
	
	private String opremaFile;
	private ArrayList<Oprema> oprema;
	
	public OpremaManager(String opremaFile) {
		this.opremaFile = opremaFile;
		this.oprema = new ArrayList<Oprema>();
	}
	
	public ArrayList<Oprema> getOprema() {
		return oprema;
	}
	
	public boolean ucitajOpremu() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.opremaFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Oprema o = new Oprema(tokeni[0]);
				this.oprema.add(o);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajOpremu() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.opremaFile, false));
			for (Oprema o : oprema) {
				pw.println(o.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}
	
	public Oprema nadjiOpremu(String nazivOpreme) {
		for (Oprema o : oprema) {
			if (o.getNaziv().equals(nazivOpreme)) {
				return o;
			}
		}
		return null;
	}
	
	public boolean dodajOpremu(String nazivOpreme) {
		if (nadjiOpremu(nazivOpreme) != null) {
			System.out.println("Oprema sa tim nazivom vec postoji!");
			return false;
		}
		Oprema o = new Oprema(nazivOpreme);
		oprema.add(o);
		return true;
	}
	
	public boolean izmeniOpremu(String nazivOpreme, String noviNaziv) {
		Oprema o = nadjiOpremu(nazivOpreme);
		if (o != null && nadjiOpremu(noviNaziv) == null) {
			o.setNaziv(noviNaziv);
			return true;
		} else {
			System.out.println("Oprema " + nazivOpreme + " ne postoji u sistemu.");
			return false;
		}
	}
	
	public boolean izbrisiOpremu(String nazivOpreme) {
		Oprema o = nadjiOpremu(nazivOpreme);
		if (o != null) {
			oprema.remove(o);
			return true;
		} else {
			System.out.println("Oprema " + nazivOpreme + " ne postoji u sistemu.");
			return false;
		}

	}
}
