package manage;

import java.util.ArrayList;

import entity.Sobarica;
import entity.Zaposleni;

public class ZaposleniManager {
	private AdministratorManager am;
	private RecepcionerManager rm;
	private SobaricaManager sm;
	
	public ZaposleniManager() {
		this.am = new AdministratorManager("data/administratori.csv");
		this.rm = new RecepcionerManager("data/recepcioneri.csv");
		this.sm = new SobaricaManager("data/sobarice.csv");
		am.ucitajAdministratore();
		rm.ucitajRecepcionere();
		sm.ucitajSobarice();
	}
	
	public ArrayList<Zaposleni> getZaposleni() {
		ArrayList<Zaposleni> zaposleni = new ArrayList<Zaposleni>();
		zaposleni.addAll(am.getAdministratori());
		zaposleni.addAll(rm.getRecepcioneri());
		zaposleni.addAll(sm.getSobarice());
		return zaposleni;
	}
	
	public void prikaziSveZaposlene() {
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
