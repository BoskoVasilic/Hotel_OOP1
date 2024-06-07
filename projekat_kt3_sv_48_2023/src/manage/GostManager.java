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
	private Gost ulogovaniGost;
	
	private GostManager(String gostFile) {
		this.gostFile = gostFile;
		this.gosti = new ArrayList<Gost>();
		this.ulogovaniGost = null;
	}
	
	private static GostManager instance;
	
	public static GostManager getInstance() {
		if (instance == null) {
			instance = new GostManager("data/gosti.csv");
			instance.ucitajGoste();
		}
		return instance;
	}
	
	public ArrayList<Gost> getGosti() {
		return gosti;
	}
	
	public void setUlogovaniGost(Gost g) {
		this.ulogovaniGost = g;
	}
	
	public Gost getUlogovaniGost() {
		return ulogovaniGost;
	}
	
	public boolean ucitajGoste() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.gostFile));
			String linija = null;
			String regex = ",(?![^\\[]*\\])";
			this.gosti.clear();
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(regex);
				Gost g = new Gost(tokeni[0], tokeni[1], Pol.valueOf(tokeni[2]), LocalDate.parse(tokeni[3]), tokeni[4], tokeni[5], tokeni[6], tokeni[7]);
				this.gosti.add(g);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajGoste() {
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
		if (nadjiGosta(email) != null) {
			System.out.println("Gost sa korisnickim imenom " + email + " vec postoji!");
			return;
		}
		gosti.add(new Gost(ime, prezime, pol, datumRodjenja, telefon, adresa, email, brojPasosa));
	}
	
	public void izmeniGosta(String korisnickoIme, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			String telefon, String adresa, String lozinka) {
		Gost g = nadjiGosta(korisnickoIme);
		if (g != null) {
			g.setIme(ime);
			g.setPrezime(prezime);
			g.setPol(pol);
			g.setDatumRodjenja(datumRodjenja);
			g.setTelefon(telefon);
			g.setAdresa(adresa);
			g.setLozinka(lozinka);
		}else {
			System.out.println("Gost sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
	
	public void obrisiGosta(String korisnickoIme) {
		Gost g = nadjiGosta(korisnickoIme);
		if (g != null) {
			gosti.remove(g);
		} else {
			System.out.println("Gost sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
}
