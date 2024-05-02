package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Pol;
import entity.Sobarica;
import entity.StrucnaSprema;

public class SobaricaManager {
	private String sobaricaFile;
	private ArrayList<Sobarica> sobarice;
	private SobaManager sm;
	
	public SobaricaManager(String sobaricaFile) {
		this.sobaricaFile = sobaricaFile;
		this.sobarice = new ArrayList<Sobarica>();
		this.sm = new SobaManager("data/sobe.txt");
	}
	
	public ArrayList<Sobarica> getSobarice() {
		return sobarice;
	}
	
	public boolean ucitajSobarice() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.sobaricaFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Sobarica s = new Sobarica(tokeni[0], tokeni[1], Pol.valueOf(tokeni[2]), LocalDate.parse(tokeni[3]), tokeni[4], tokeni[5], tokeni[6], tokeni[7], StrucnaSprema.valueOf(tokeni[8]), Integer.parseInt(tokeni[9]));
				s.setBrojSobaZaSredjivanje(Integer.parseInt(tokeni[11]));
				for (String brojSobe : tokeni[11].substring(1, tokeni[12].length() - 1).split(", ")) {
					if(!brojSobe.equals("")){
						s.getSobeZaSredjivanje().add(sm.nadjiSobu(Integer.parseInt(brojSobe)));
					}
				}
				LocalDate danasnjiDatum = LocalDate.now();
				LocalDate datum = LocalDate.parse(tokeni[13]);
				if (danasnjiDatum.isEqual(datum) != true) {
					s.setDatum(danasnjiDatum);
					s.setBrojSobaZaSredjivanje(0);
				}
				this.sobarice.add(s);
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
			pw = new PrintWriter(new FileWriter(this.sobaricaFile, false));
			for (Sobarica s : sobarice) {
				pw.println(s.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Sobarica nadjiSobaricu(String korisnickoIme) {
		for (Sobarica s : sobarice) {
			if (s.getKorisnickoIme().equals(korisnickoIme)) {
				return s;
			}
		}
		return null;
	}
	
	public void dodajSobaricu(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		sobarice.add(new Sobarica(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, godineStaza));
	}
	
	public void izmeniSobaricu(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		Sobarica sobarica = nadjiSobaricu(korisnickoIme);
		if (sobarica != null) {
			sobarica.setIme(ime);
			sobarica.setPrezime(prezime);
			sobarica.setPol(pol);
			sobarica.setDatumRodjenja(datumRodjenja);
			sobarica.setTelefon(telefon);
			sobarica.setAdresa(adresa);
			sobarica.setKorisnickoIme(korisnickoIme);
			sobarica.setLozinka(lozinka);
			sobarica.setStrucnaSprema(strucnaSprema);
			sobarica.setGodineStaza(godineStaza);
		}
		else {
			System.out.println("Sobarica sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
	
	public void obrisiRecepcionera(String korisnickoIme) {
		Sobarica sobarica = nadjiSobaricu(korisnickoIme);
		if (sobarica != null) {
			sobarice.remove(sobarica);
		} else {
			System.out.println("Sobarica sa korisnickim imenom " + korisnickoIme + " ne postoji!");
		}
	}
	
}
