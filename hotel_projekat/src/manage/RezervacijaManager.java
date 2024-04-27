package manage;

import java.util.ArrayList;

import entity.Rezervacija;

public class RezervacijaManager {
	
	private String rezervacijaFile;
	private ArrayList<Rezervacija> rezervacije;
	
	public RezervacijaManager(String rezervacijaFile) {
		this.rezervacijaFile = rezervacijaFile;
		this.rezervacije = new ArrayList<Rezervacija>();
	}
	
	public ArrayList<Rezervacija> getRezervacije() {
		return rezervacije;
	}
	
	public Rezervacija nadjiRezervaciju(int id) {
		for (Rezervacija r : rezervacije) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}
}
