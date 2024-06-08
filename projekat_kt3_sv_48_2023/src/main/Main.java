package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import entity.DodatnaUsluga;
import entity.Oprema;
import entity.Pol;
import entity.StatusSobe;
import entity.StrucnaSprema;
import manage.AdministratorManager;
import manage.CenovnikManager;
import manage.DodatnaUslugaManager;
import manage.GostManager;
import manage.OpremaManager;
import manage.RecepcionerManager;
import manage.RezervacijaManager;
import manage.SobaManager;
import manage.SobaricaManager;
import manage.TipSobeManager;
import manage.ZaposleniManager;
import view.LoginUI;

public class Main {

	public static void main(String[] args) {
		//kt2
		/*DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	
		AdministratorManager am = new AdministratorManager("data/administratori.csv");
		am.ucitajAdministratore();
		am.dodajAdministratora("Pera", "Peric", Pol.M, LocalDate.parse("01.01.1990.", format), "0645211452", "Bulevar Oslobodjenja 2", "pera", "pera123", StrucnaSprema.VI, 5);
		am.sacuvajAdministratore();
		
		RecepcionerManager rm = new RecepcionerManager("data/recepcioneri.csv");
		rm.ucitajRecepcionere();
		rm.dodajRecepcionera("Mika", "Mikic", Pol.M, LocalDate.parse("23.04.1987.", format), "0631445287", "Bulevar Kralja Petra I 15", "mika", "mika123", StrucnaSprema.V, 3);
		rm.dodajRecepcionera("Nikola", "Nikolic", Pol.M, LocalDate.parse("12.05.1970.", format), "0643332122", "Jevrejska 9", "nikola", "nikola123", StrucnaSprema.IV, 20);
		rm.sacuvajRecepcionere();
		
		SobaricaManager sm = SobaricaManager.getInstance();
		sm.ucitajSobarice();
		sm.dodajSobaricu("Jana", "Janic", Pol.Z, LocalDate.parse("15.08.1995.", format), "0655165075", "Bulevar Vojvode Stepe 15", "jana", "jana123", StrucnaSprema.III, 10);
		sm.sacuvajSobarice();
		
		ZaposleniManager zm = new ZaposleniManager();
		zm.prikaziSveZaposlene();
		
		rm.obrisiRecepcionera("nikola");
		rm.sacuvajRecepcionere();
		
		GostManager gm = GostManager.getInstance();
		gm.dodajGosta("Milica", "Milic", Pol.Z, LocalDate.parse("12.02.1980.", format), "063128416", "Fruskogorska 12", "milica@gmail.com", "002415874");
		gm.dodajGosta("Ana", "Anic", Pol.Z, LocalDate.parse("25.05.1998.", format), "0641220906", "Bate Brkica 3", "ana@gmail.com", "002312398");
		gm.sacuvajGoste();
		
		TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
		tsm.ucitajTipoveSoba();
		tsm.dodajTipSobe("jednokrevetna (1)", 1, 1);
		tsm.dodajTipSobe("dvokrevetna (2)", 1, 2);
		tsm.dodajTipSobe("dvokrevetna (1+1)", 2, 2);
		tsm.dodajTipSobe("trokrevetna (2+1)", 2, 3);
		tsm.sacuvajTipoveSoba();
		
		OpremaManager om = new OpremaManager("data/oprema.csv");
		om.ucitajOpremu();
		om.dodajOpremu("TV");
		om.dodajOpremu("Klima");
		om.dodajOpremu("Mini-bar");
		om.sacuvajOpremu();
		
		SobaManager som = new SobaManager("data/sobe.csv");
		som.ucitajSobe();
		som.dodajSobu(1, tsm.nadjiTipSobe("jednokrevetna (1)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("TV"), om.nadjiOpremu("Klima"))), false);
		som.dodajSobu(2, tsm.nadjiTipSobe("dvokrevetna (2)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("TV"), om.nadjiOpremu("Klima"), om.nadjiOpremu("Mini-bar"))), true);
		som.dodajSobu(3, tsm.nadjiTipSobe("dvokrevetna (1+1)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("TV"), om.nadjiOpremu("Klima"))), false);
		som.dodajSobu(4, tsm.nadjiTipSobe("trokrevetna (2+1)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("Klima"), om.nadjiOpremu("Mini-bar"))), true);
		som.dodajSobu(5, tsm.nadjiTipSobe("dvokrevetna (2)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("TV"), om.nadjiOpremu("Klima"))), true);
		som.sacuvajSobe();
		
		som.izmeniSobu(2, tsm.nadjiTipSobe("trokrevetna (2+1)"), StatusSobe.SLOBODNA, new ArrayList<Oprema>(Arrays.asList(om.nadjiOpremu("TV"), om.nadjiOpremu("Klima"), om.nadjiOpremu("Mini-bar"))), true);
		som.sacuvajSobe();
		
		DodatnaUslugaManager dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
		dum.ucitajDodatneUsluge();
		dum.dodajDodatnuUslugu("dorucak");
		dum.dodajDodatnuUslugu("rucak");
		dum.dodajDodatnuUslugu("vecera");
		dum.dodajDodatnuUslugu("bazen");
		dum.dodajDodatnuUslugu("spa centar");
		dum.sacuvajDodatneUsluge();
		
		dum.obrisiDodatnuUslugu("spa centar");
		dum.sacuvajDodatneUsluge();
		
		CenovnikManager cm = new CenovnikManager("data/cenovnik.csv");
		cm.ucitajCenovnike();
		cm.dodajCenovnik(LocalDate.parse("01.01.2024.", format), LocalDate.parse("31.12.2024.", format));
		cm.sacuvajCenovnike();
		
		cm.izmeniStavkuCenovnika(LocalDate.parse("01.01.2024.", format), LocalDate.parse("31.12.2024.", format), "DodatneUsluge", "dorucak", 450.0);
		cm.sacuvajCenovnike();
		
		RezervacijaManager rezM = RezervacijaManager.getInstance();
		
		//rezM.ispisiSlobodneTipoveSoba(LocalDate.parse("01.08.2024.", format), LocalDate.parse("31.08.2024.", format));
		
		rezM.dodajRezervacijuPoTipu(gm.nadjiGosta("milica@gmail.com"), LocalDate.parse("13.08.2024.", format), LocalDate.parse("23.08.2024.", format), tsm.nadjiTipSobe("trokrevetna (2+1)"), 3, new ArrayList<DodatnaUsluga>(Arrays.asList(dum.nadjiDodatnuUslugu("dorucak"), dum.nadjiDodatnuUslugu("vecera"))), new ArrayList<Oprema>());
		rezM.sacuvajRezervacije();
		
		//rezM.ispisiSlobodneTipoveSoba(LocalDate.parse("01.06.2024.", format), LocalDate.parse("30.06.2024.", format));
		
		rezM.dodajRezervacijuPoTipu(gm.nadjiGosta("ana@gmail.com"), LocalDate.parse("06.06.2024.", format), LocalDate.parse("12.06.2024.", format), tsm.nadjiTipSobe("dvokrevetna (1+1)"), 2,new ArrayList<DodatnaUsluga>(), new ArrayList<Oprema>());
		rezM.sacuvajRezervacije();
		
		rezM.prikaziRezervacijeZaGosta(gm.nadjiGosta("milica@gmail.com"));*/
		
		//kt3
		LoginUI frame = new LoginUI();
		frame.setVisible(true);
		
	}

}
