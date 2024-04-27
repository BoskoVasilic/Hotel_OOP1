package manage;

import entity.Sobarica;
import entity.Zaposleni;

public class ZaposleniManager {
	
	public void prikaziSveZaposlene(AdministratorManager am, RecepcionerManager rm, SobaricaManager sm) {
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
		System.out.println("Sobarice: ");
		System.out.println("------------------------------");
		for (Sobarica s : sm.getSobarice()) {
			System.out.println(s);
		}
		System.out.println("------------------------------");
	}
}
