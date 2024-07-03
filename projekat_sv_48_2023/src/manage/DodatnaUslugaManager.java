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
	
	public boolean dodajDodatnuUslugu(String naziv) {
		if (this.nadjiDodatnuUslugu(naziv) != null) {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " vec postoji!");
			return false;
		}
		DodatnaUsluga du = new DodatnaUsluga(naziv);
		dodatneUsluge.add(du);
		return true;
	}
	
	public boolean izmeniDodatnuUslugu(String naziv, String noviNaziv) {
		DodatnaUsluga du = this.nadjiDodatnuUslugu(naziv);
		if (du != null && this.nadjiDodatnuUslugu(noviNaziv) == null) {
			du.setNaziv(noviNaziv);
			return true;
		} else {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " ne postoji!");
			return false;
		}
	}
	
	public boolean obrisiDodatnuUslugu(String naziv) {
		DodatnaUsluga du = this.nadjiDodatnuUslugu(naziv);
		if (du != null) {
			dodatneUsluge.remove(du);
			return true;
		} else {
			System.out.println("Dodatna usluga sa nazivom " + naziv + " ne postoji!");
			return false;
		}
	}
}
