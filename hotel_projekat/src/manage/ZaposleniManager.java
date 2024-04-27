package manage;

import entity.Zaposleni;

public class ZaposleniManager {
	
	public void prikaziSveZaposlene(AdministratorManager am, RecepcionerManager rm) {
		System.out.println("------------------------------");
		System.out.println("Administratori: ");
		System.out.println("------------------------------");
		for (Zaposleni z : am.getAdministratori()) {
			System.out.println(z);
		}
		System.out.println("------------------------------");
		System.out.println("Recepcioneri: ");
		System.out.println("------------------------------");
		for (Zaposleni z : rm.getRecepcioneri()) {
			System.out.println(z);
		}
		System.out.println("------------------------------");
	}
}
