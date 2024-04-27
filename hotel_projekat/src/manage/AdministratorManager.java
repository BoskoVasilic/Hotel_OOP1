package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Osnovica;
import entity.Pol;
import entity.StrucnaSprema;
import entity.Zaposleni;

public class AdministratorManager {
	private String adminFile;
	private ArrayList<Zaposleni> administratori;
	
	
	public AdministratorManager(String adminFile) {
		this.adminFile = adminFile;
		this.administratori = new ArrayList<Zaposleni>();
	}
	
	
	public ArrayList<Zaposleni> getAdministratori() {
		return administratori;
	}


	public boolean ucitajAdministratore() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.adminFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Zaposleni z = new Zaposleni(tokeni[0], tokeni[1], Pol.valueOf(tokeni[2]), LocalDate.parse(tokeni[3]), tokeni[4], tokeni[5], tokeni[6], tokeni[7], StrucnaSprema.valueOf(tokeni[8]), Integer.parseInt(tokeni[9]), Osnovica.Administarator);
				this.administratori.add(z);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajAdministratore() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.adminFile, false));
			for (Zaposleni a : administratori) {
				pw.println(a.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Zaposleni nadjiAdministratora(String korisnickoIme) {
		for (Zaposleni a : administratori) {
			if (a.getKorisnickoIme().equals(korisnickoIme)) {
				return a;
			}
		}
		return null;
	}
	
	public void dodajAdministratora(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		administratori.add(new Zaposleni(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, godineStaza, Osnovica.Administarator));
	}
	
	public void izmeniAdministratora(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		Zaposleni admin = nadjiAdministratora(korisnickoIme);
		if (admin != null) {
			admin.setIme(ime);
			admin.setPrezime(prezime);
			admin.setPol(pol);
			admin.setDatumRodjenja(datumRodjenja);
			admin.setTelefon(telefon);
			admin.setAdresa(adresa);
			admin.setKorisnickoIme(korisnickoIme);
			admin.setLozinka(lozinka);
			admin.setStrucnaSprema(strucnaSprema);
			admin.setGodineStaza(godineStaza);
		}
		else {
			System.out.println("Administrator sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
	
	public void obrisiAdministratora(String korisnickoIme) {
		Zaposleni admin = nadjiAdministratora(korisnickoIme);
		if (admin != null) {
			administratori.remove(admin);
		} else {
			System.out.println("Administrator sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
	
}
