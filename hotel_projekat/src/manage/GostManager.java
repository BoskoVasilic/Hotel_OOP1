package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Gost;
import entity.Pol;

public class GostManager {
	
	private String gostFile;
	private ArrayList<Gost> gosti;
	private RezervacijaManager rm;
	
	public GostManager(String gostFile) {
		this.gostFile = gostFile;
		this.gosti = new ArrayList<Gost>();
		this.rm = new RezervacijaManager("data/rezervacije.csv");
	}
	
	public ArrayList<Gost> getGosti() {
		return gosti;
	}
	
	public boolean ucitajGoste() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.gostFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Gost g = new Gost(tokeni[0], tokeni[1], Pol.valueOf(tokeni[2]), LocalDate.parse(tokeni[3]), tokeni[4], tokeni[5], tokeni[6], tokeni[7]);
				for (String idRezervacije : tokeni[8].substring(1, tokeni[11].length() - 1).split(", ")) {
					g.getRezervacije().add(rm.nadjiRezervaciju(Integer.parseInt(idRezervacije)));
				}
				this.gosti.add(g);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajSobarice() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.gostFile, false));
			for (Gost g : gosti) {
				pw.println(g.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Gost nadjiGosta(String korisnickoIme) {
		for (Gost g : gosti) {
			if (g.getKorisnickoIme().equals(korisnickoIme)) {
				return g;
			}
		}
		return null;
	}
	
	public void dodajGosta(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa, String email, String brojPasosa) {
		gosti.add(new Gost(ime, prezime, pol, datumRodjenja, telefon, adresa, email, brojPasosa));
	}
	
}
