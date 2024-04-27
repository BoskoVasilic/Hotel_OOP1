package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sobarica extends Zaposleni {
	protected int brojSobaZaSredjivanje;
	protected ArrayList<Soba> sobeZaSredjivanje;
	protected LocalDate datum;
	
	public Sobarica(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, godineStaza,
				Osnovica.Sobarica);
		this.brojSobaZaSredjivanje = 0;
		this.sobeZaSredjivanje = new ArrayList<Soba>();
		this.datum = LocalDate.now();
	}

	public int getBrojSobaZaSredjivanje() {
		return brojSobaZaSredjivanje;
	}

	public void setBrojSobaZaSredjivanje(int brojSobaZaSredjivanje) {
		this.brojSobaZaSredjivanje = brojSobaZaSredjivanje;
	}

	public ArrayList<Soba> getSobeZaSredjivanje() {
		return sobeZaSredjivanje;
	}

	public void setSobeZaSredjivanje(ArrayList<Soba> sobeZaSredjivanje) {
		this.sobeZaSredjivanje = sobeZaSredjivanje;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	@Override
	public String toString() {
		ArrayList<Integer> brojeviSoba = new ArrayList<Integer>();
		for (Soba s : sobeZaSredjivanje) {
			brojeviSoba.add(s.getBrojSobe());
		}
		return super.toString() + "\nBroj soba za sredjivanje dodeljenih danas: " + brojSobaZaSredjivanje + "\nTrenutno dodeljene sobe: " + brojeviSoba;
	}
	
	public String toFile() {
		ArrayList<Integer> brojeviSoba = new ArrayList<Integer>();
		for (Soba s : sobeZaSredjivanje) {
			brojeviSoba.add(s.getBrojSobe());
		}
		return super.toFile() + ", " + brojSobaZaSredjivanje + ", " + brojeviSoba + ", " + datum;
	}
		
}
