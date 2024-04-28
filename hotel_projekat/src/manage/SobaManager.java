package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Gost;
import entity.Pol;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;

public class SobaManager {
	String sobaFile;
	ArrayList<Soba> sobe;
	
	public SobaManager(String sobaFile) {
		this.sobaFile = sobaFile;
		this.sobe = new ArrayList<Soba>();
	}
	
	public ArrayList<Soba> getSobe() {
		return sobe;
	}
	
	public boolean ucitajSobe() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.sobaFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Soba s = new Soba(Integer.parseInt(tokeni[0]), StatusSobe.valueOf(tokeni[2]), Boolean.parseBoolean(tokeni[4]));
				for (String nazivOpreme : tokeni[3].substring(1, tokeni[3].length() - 1).split(", ")) {
					//g.getRezervacije().add(rm.nadjiRezervaciju(Integer.parseInt(idRezervacije)));
				}
				//s.setTipSobe(ts.nadjiTipSobe(Integer.parseInt(tokeni[1])));
				this.sobe.add(s);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajSobe() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.sobaFile, false));
			for (Soba s : sobe) {
				pw.println(s.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;

	}
	
	public Soba nadjiSobu(int brojSobe) {
		for (Soba s : sobe) {
			if (s.getBrojSobe() == brojSobe) {
				return s;
			}
		}
		return null;
	}
	
}
