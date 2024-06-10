package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import entity.Cenovnik;
import entity.DodatnaUsluga;
import entity.TipSobe;

public class CenovnikManager {
	private String cenovnikFile;
	private ArrayList<Cenovnik> cenovnici;
	private TipSobeManager tsm;
	private DodatnaUslugaManager dum;
	
	public CenovnikManager(String cenovnikFile) {
		this.cenovnikFile = cenovnikFile;
		this.cenovnici = new ArrayList<Cenovnik>();
		this.tsm = new TipSobeManager("data/tipoviSoba.csv");
		this.dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
		tsm.ucitajTipoveSoba();
		dum.ucitajDodatneUsluge();
	}
	
	public ArrayList<Cenovnik> getCenovnici() {
		return cenovnici;
	}
	
	public boolean ucitajCenovnike() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.cenovnikFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				HashMap<String, HashMap<String, Double>> cene = new HashMap<String, HashMap<String, Double>>();
				String grupa = tokeni[2];
				cene.put(tokeni[2], new HashMap<String, Double>());
				for (int i = 3; i < tokeni.length; i++) {
					if(tokeni[i].equals("")) {
						cene.put(tokeni[i + 1], new HashMap<String, Double>());
						grupa = tokeni[i + 1];
						i++;
					} else {
						String[] stavka = tokeni[i].split("=");
						cene.get(grupa).put(stavka[0], Double.parseDouble(stavka[1]));
					}
				}
				Cenovnik c = new Cenovnik(LocalDate.parse(tokeni[0]), LocalDate.parse(tokeni[1]), cene);
				this.cenovnici.add(c);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajCenovnike() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.cenovnikFile, false));
			for (Cenovnik c : cenovnici) {
				pw.println(c.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}
	
	public Cenovnik nadjiCenovnikZaDatum(LocalDate datum) {
		for (Cenovnik c : cenovnici) {
			if (datum.isAfter(c.getPocetakVazenja()) && datum.isBefore(c.getKrajVazenja())) {
				return c;
			}
		}
		return null;
	}
	
	public Cenovnik nadjiCenovnik(LocalDate pocetakVazenja, LocalDate krajVazenja) {
		for (Cenovnik c : cenovnici) {
			if (c.getPocetakVazenja().isEqual(pocetakVazenja) && c.getKrajVazenja().isEqual(krajVazenja)) {
				return c;
			}
		}
		return null;
	}
	
	private boolean proveriPreklapanjeDatuma(LocalDate pocetakVazenja, LocalDate krajVazenja, LocalDate pocetakVazenjaNovi, LocalDate krajVazenjaNovi) {
		return !(krajVazenja.isBefore(pocetakVazenjaNovi) || pocetakVazenja.isAfter(krajVazenjaNovi));
	}
	
	private HashMap<String, HashMap<String, Double>> unesiCene() {
		Scanner sc = new Scanner(System.in);
		HashMap<String, HashMap<String, Double>> cene = new HashMap<String, HashMap<String, Double>>();
		System.out.println("\nUnesite cene za cenovnik:");
		ArrayList<TipSobe> tipoviSoba = tsm.getTipoviSoba();
		cene.put("TipoviSoba", new HashMap<String, Double>());
		for (TipSobe ts : tipoviSoba) {
			System.out.println("Unesite cenu za tip sobe: " + ts.getNaziv());
			double cena = sc.nextDouble();
			sc.nextLine();
			cene.get("TipoviSoba").put(ts.getNaziv(), cena);
		}
		ArrayList<DodatnaUsluga> dodatneUsluge = dum.getDodatneUsluge();
		cene.put("DodatneUsluge", new HashMap<String, Double>());
		for (DodatnaUsluga du : dodatneUsluge) {
			System.out.println("Unesite cenu za dodatnu uslugu: " + du.getNaziv());
			double cena = sc.nextDouble();
			sc.nextLine();
			cene.get("DodatneUsluge").put(du.getNaziv(), cena);
		}
		sc.close();
			System.out.println("Uspesno ste uneli cene za cenovnik.\n");
		return cene;
    }
	
	public void dodajCenovnik(LocalDate pocetakVazenja, LocalDate krajVazenja) {
		for(Cenovnik c : cenovnici) {
			if (proveriPreklapanjeDatuma(c.getPocetakVazenja(), c.getKrajVazenja(), pocetakVazenja, krajVazenja)) {
				System.out.println("Vec postoji cenovnik za uneti period!");
				return;
			}
		}
		HashMap<String, HashMap<String, Double>> cene = unesiCene();
		Cenovnik c = new Cenovnik(pocetakVazenja, krajVazenja, cene);
		this.cenovnici.add(c);
	}
	
	public boolean dodajCenovnikGui(LocalDate pocetakVazenja, LocalDate krajVazenja, HashMap<String, HashMap<String, Double>> cene) {
		for(Cenovnik c : cenovnici) {
			if (proveriPreklapanjeDatuma(c.getPocetakVazenja(), c.getKrajVazenja(), pocetakVazenja, krajVazenja)) {
				System.out.println("Vec postoji cenovnik za uneti period!");
				return false;
			}
		}
		Cenovnik c = new Cenovnik(pocetakVazenja, krajVazenja, cene);
		this.cenovnici.add(c);
		return true;
	}
	
	public void izmeniCenovnikKomplet(LocalDate pocetakVazenja, LocalDate krajVazenja) {
		Cenovnik c = nadjiCenovnik(pocetakVazenja, krajVazenja);
		if (c != null) {
			HashMap<String, HashMap<String, Double>> cene = unesiCene();
			c.setCene(cene);
		} else {
			System.out.println("Cenovnik za uneti period ne postoji u sistemu.");
		}
	}
	
	public void izmeniCenovnikKompletGUI(LocalDate pocetakVazenja, LocalDate krajVazenja, HashMap<String, HashMap<String, Double>> cene) {
		Cenovnik c = nadjiCenovnik(pocetakVazenja, krajVazenja);
		if (c != null) {
			c.setCene(cene);
		} else {
			System.out.println("Cenovnik za uneti period ne postoji u sistemu.");
		}
	}
	
	public void izmeniStavkuCenovnika(LocalDate pocetakVazenja, LocalDate krajVazenja, String grupa, String stavka,
			double cena) {
		Cenovnik c = nadjiCenovnik(pocetakVazenja, krajVazenja);
		if (c != null) {
			if (c.getCene().containsKey(grupa)) {
				c.getCene().get(grupa).put(stavka, cena);
			} else {
				System.out.println("Grupa " + grupa + " ne postoji u cenovniku.");
			}
		} else {
			System.out.println("Cenovnik za uneti period ne postoji u sistemu.");
		}
	}
	
	public void obrisiCenovnik(LocalDate pocetakVazenja, LocalDate krajVazenja) {
		Cenovnik c = nadjiCenovnik(pocetakVazenja, krajVazenja);
		if (c != null) {
			cenovnici.remove(c);
		} else {
			System.out.println("Cenovnik za uneti period ne postoji u sistemu.");
		}
	}
}
