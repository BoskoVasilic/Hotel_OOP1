package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gost extends Korisnik {
	protected ArrayList<Rezervacija> rezervacije;
	
	public Gost(String ime, String prezime, Pol pol, LocalDate datumRodjenja , String telefon, String adresa,
			String email, String brojPasosa) {
		super(ime, prezime, pol,  datumRodjenja, telefon, adresa, email, brojPasosa);
		this.rezervacije = new ArrayList<Rezervacija>();
	}

	public ArrayList<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(ArrayList<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}
	
	@Override
	public String toString() {
		ArrayList<Integer> idRezervacija = new ArrayList<Integer>();
		for (Rezervacija r : rezervacije) {
			idRezervacija.add(r.getId());
		}
		return super.toString() + "\nRezervacije: " + idRezervacija;
	}
	
	public String toFile() {
		ArrayList<Integer> idRezervacija = new ArrayList<Integer>();
		for (Rezervacija r : rezervacije) {
			idRezervacija.add(r.getId());
		}
		return super.toFile() + "," + idRezervacija;
	}
	
}
