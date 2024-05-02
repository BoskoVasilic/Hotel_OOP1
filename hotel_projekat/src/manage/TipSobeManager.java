package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entity.TipSobe;

public class TipSobeManager {
	
	private String tipSobeFile;
	private ArrayList<TipSobe> tipoviSoba;
	
	public TipSobeManager(String tipSobeFile) {
		this.tipSobeFile = tipSobeFile;
		this.tipoviSoba = new ArrayList<TipSobe>();
	}
	
	public TipSobe[] getTipoviSoba() {
		System.out.println("*************************" + this.tipoviSoba.size());
		return this.tipoviSoba.toArray(new TipSobe[tipoviSoba.size()]);
	}
	
	public boolean ucitajTipoveSoba() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.tipSobeFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				TipSobe ts = new TipSobe(tokeni[0], Integer.parseInt(tokeni[1]), Integer.parseInt(tokeni[2]));
				System.out.println(ts);
				this.tipoviSoba.add(ts);
				System.out.println(this.tipoviSoba + "---------------" + tipoviSoba.size());
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajTipoveSoba() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.tipSobeFile, false));
			for (TipSobe tp : tipoviSoba) {
				pw.println(tp.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}

    
	public TipSobe nadjiTipSobe(String naziv) {
		for (TipSobe ts : tipoviSoba) {
			if (ts.getNaziv().equals(naziv)) {
				return ts;
			}
		}
		return null;
	}

	public void izmeniTipSobe(String naziv, int brojKreveta, int brojOsoba) {
		TipSobe ts = this.nadjiTipSobe(naziv);
		if (ts != null) {
			ts.setBrojKreveta(brojKreveta);
			ts.setBrojOsoba(brojOsoba);
		} else {
			System.out.println("Tip sobe " + naziv + " ne postoji u sistemu.");
		}
	}
	
	public void obrisiTipSobe(String naziv) {
		TipSobe ts = this.nadjiTipSobe(naziv);
		if (ts != null) {
			tipoviSoba.remove(ts);
		} else {
			System.out.println("Tip sobe " + naziv + " ne postoji u sistemu.");
		}
	}
	
	
}
