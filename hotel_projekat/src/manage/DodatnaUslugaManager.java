package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entity.DodatnaUsluga;

public class DodatnaUslugaManager {
	
	private String dodatnaUslugaFile;
	private ArrayList<DodatnaUsluga> dodatneUsluge;
	
	public DodatnaUslugaManager(String dodatnaUslugaFile) {
		this.dodatnaUslugaFile = dodatnaUslugaFile;
		this.dodatneUsluge = new ArrayList<DodatnaUsluga>();
	}
	
	public ArrayList<DodatnaUsluga> getDodatneUsluge() {
		return dodatneUsluge;
	}
	
	public boolean ucitajDodatneUsluge() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.dodatnaUslugaFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				DodatnaUsluga du = new DodatnaUsluga(tokeni[0]);
				this.dodatneUsluge.add(du);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajDodatneUsluge() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.dodatnaUslugaFile, false));
			for (DodatnaUsluga du : dodatneUsluge) {
				pw.println(du.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}
	
	public DodatnaUsluga nadjiDodatnuUslugu(String naziv) {
		for (DodatnaUsluga du : dodatneUsluge) {
			if (du.getNaziv().equals(naziv)) {
				return du;
			}
		}
		return null;
	}
	
	public void dodajDodatnuUslugu(String naziv) {
		if (this.nadjiDodatnuUslugu(naziv) != null) {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " vec postoji!");
			return;
		}
		DodatnaUsluga du = new DodatnaUsluga(naziv);
		dodatneUsluge.add(du);
	}
	
	public void izmeniDodatnuUslugu(String naziv, String noviNaziv) {
		DodatnaUsluga du = this.nadjiDodatnuUslugu(naziv);
		if (du != null) {
			du.setNaziv(noviNaziv);
		} else {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " ne postoji!");
		}
	}
	
	public void obrisiDodatnuUslugu(String naziv) {
		DodatnaUsluga du = this.nadjiDodatnuUslugu(naziv);
		if (du != null) {
			dodatneUsluge.remove(du);
		} else {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " ne postoji!");
		}
	}
}
