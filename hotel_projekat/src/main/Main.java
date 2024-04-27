package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entity.Pol;
import entity.StrucnaSprema;
import manage.AdministratorManager;
import manage.RecepcionerManager;
import manage.ZaposleniManager;

public class Main {

	public static void main(String[] args) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	
		AdministratorManager am = new AdministratorManager("data/administratori.csv");
		am.ucitajAdministratore();
		//am.dodajAdministratora("Pera", "Peric", Pol.M, LocalDate.parse("01.01.1990.", format), "0645211452", "Bulevar Oslobodjenja 2", "pera", "pera123", StrucnaSprema.VI, 5);
		//am.sacuvajAdministratore();
		
		RecepcionerManager rm = new RecepcionerManager("data/recepcioneri.csv");
		rm.ucitajRecepcionere();
		//rm.dodajRecepcionera("Mika", "Mikic", Pol.M, LocalDate.parse("23.04.1987.", format), "0631445287", "Bulevar Kralja Petra I 15", "mika", "mika123", StrucnaSprema.V, 3);
		//rm.dodajRecepcionera("Nikola", "Nikolic", Pol.M, LocalDate.parse("12.05.1970.", format), "0643332122", "Jevrejska 9", "nikola", "nikola123", StrucnaSprema.IV, 20);
		//rm.sacuvajRecepcionere();
		
		ZaposleniManager zm = new ZaposleniManager();
		zm.prikaziSveZaposlene(am, rm);
		
		rm.obrisiRecepcionera("nikola");
	}

}
