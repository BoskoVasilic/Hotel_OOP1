package manage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entity.Osnovica;
import entity.Pozicija;

public class OsnovicaManager {
	private String osnovicaFile;
	private ArrayList<Osnovica> osnovice;
	
	private OsnovicaManager(String osnovicaFile) {
		this.osnovicaFile = osnovicaFile;
		this.osnovice = new ArrayList<Osnovica>();
	}
	
	private static OsnovicaManager instance;
	
	public static OsnovicaManager getInstance() {
		if (instance == null) {
			instance = new OsnovicaManager("data/osnovice.csv");
			instance.ucitajOsnovice();
		}
		return instance;
	}
	
	public ArrayList<Osnovica> getOsnovica() {
		return osnovice;
	}
	
	public boolean ucitajOsnovice() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.osnovicaFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split(",");
				Osnovica o = new Osnovica(Double.parseDouble(tokeni[0]), Pozicija.valueOf(tokeni[1]));
				this.osnovice.add(o);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja fajla!");
			return false;
		}
		return true;
	}
	
	
	public boolean sacuvajOpremu() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.osnovicaFile, false));
			for (Osnovica o : osnovice) {
				pw.println(o.toFile());
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisa u fajl!");
		}
		return true;
	}
	
	public Osnovica nadjiOsnovicu(Pozicija pozicija) {
		for (Osnovica o : osnovice) {
			if (o.getPozicija() == pozicija) {
				return o;
			}
		}
		return null;
	}
}
