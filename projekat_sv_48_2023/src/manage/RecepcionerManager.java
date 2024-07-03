package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Pol;
import entity.Pozicija;
import entity.StrucnaSprema;
import entity.Zaposleni;
import entity.Osnovica;

public class RecepcionerManager {
	private String recepcionerFile;
	private ArrayList<Zaposleni> recepcioneri;
	private OsnovicaManager om = OsnovicaManager.getInstance();
	
	public RecepcionerManager(String recepcionerFile) {
		this.recepcionerFile = recepcionerFile;
		this.recepcioneri = new ArrayList<Zaposleni>();
	}
	
	public ArrayList<Zaposleni> getRecepcioneri() {
		return recepcioneri;
	}
	
	public boolean ucitajRecepcionere() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.recepcionerFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Zaposleni z = new Zaposleni(tokeni[0], tokeni[1], Pol.valueOf(tokeni[2]), LocalDate.parse(tokeni[3]), tokeni[4], tokeni[5], tokeni[6], tokeni[7], StrucnaSprema.valueOf(tokeni[8]), Integer.parseInt(tokeni[9]), new Osnovica(om.nadjiOsnovicu(Pozicija.Recepcioner).getVrednost(), Pozicija.Recepcioner));
				this.recepcioneri.add(z);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajRecepcionere() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.recepcionerFile, false));
			for (Zaposleni r : recepcioneri) {
				pw.println(r.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Zaposleni nadjiRecepcionera(String korisnickoIme) {
		for (Zaposleni r : recepcioneri) {
			if (r.getKorisnickoIme().equals(korisnickoIme)) {
				return r;
			}
		}
		return null;
	}
	
	public boolean dodajRecepcionera(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		if (nadjiRecepcionera(korisnickoIme) != null) {
			System.out.println("Recepcioner sa korisnickim imenom " + korisnickoIme + " vec postoji!");
			return false;
		}
		recepcioneri.add(new Zaposleni(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, godineStaza, new Osnovica(om.nadjiOsnovicu(Pozicija.Recepcioner).getVrednost(), Pozicija.Recepcioner)));
		return true;
	}
	
	public boolean izmeniRecepcionera(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		Zaposleni recepcioner = nadjiRecepcionera(korisnickoIme);
		if (recepcioner != null) {
			recepcioner.setIme(ime);
			recepcioner.setPrezime(prezime);
			recepcioner.setPol(pol);
			recepcioner.setDatumRodjenja(datumRodjenja);
			recepcioner.setTelefon(telefon);
			recepcioner.setAdresa(adresa);
			recepcioner.setKorisnickoIme(korisnickoIme);
			recepcioner.setLozinka(lozinka);
			recepcioner.setStrucnaSprema(strucnaSprema);
			recepcioner.setGodineStaza(godineStaza);
			return true;
		}
		else {
			System.out.println("Recepcioner sa korisnickim imenom " + korisnickoIme + " ne postoji!");
			return false;
		}
	}
	
	public boolean obrisiRecepcionera(String korisnickoIme) {
		Zaposleni recepcioner = nadjiRecepcionera(korisnickoIme);
		if (recepcioner != null) {
			recepcioneri.remove(recepcioner);
			return true;
		} else {
			System.out.println("Recepcioner sa korisnickim imenom " + korisnickoIme + " ne postoji!");
			return false;
		}
	}
	
}
