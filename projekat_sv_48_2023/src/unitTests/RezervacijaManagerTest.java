package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import entity.DodatnaUsluga;
import entity.Gost;
import entity.Oprema;
import entity.Pol;
import entity.Rezervacija;
import entity.StatusRezervacije;
import entity.TipSobe;
import manage.RezervacijaManager;

public class RezervacijaManagerTest {
	private RezervacijaManager rezervacijaManager;

	@Before
	public void setUp() {
		rezervacijaManager = RezervacijaManager.getInstance();
	}
	
	@Test
    public void testDodajRezervacijuPoTipu() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        Gost gost = new Gost("Milica", "Milic", Pol.Z, LocalDate.parse("12.02.1980.", format), "063128416", "Fruskogorska 12", "milica@gmail.com", "002415874");
        LocalDate datumPrijave = LocalDate.of(2024, 7, 1);
        LocalDate datumOdjave = LocalDate.of(2024, 7, 10);
        TipSobe tipSobe = new TipSobe("dvokrevetna (2)", 1, 2); 
        int brojLjudi = 2;
        ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<>();
        ArrayList<Oprema> zahtevanaOprema = new ArrayList<>();

        rezervacijaManager.dodajRezervacijuPoTipu(gost, datumPrijave, datumOdjave, tipSobe, brojLjudi, dodatneUsluge, zahtevanaOprema);
        
        ArrayList<Rezervacija> rezervacije = rezervacijaManager.getRezervacije();
        assertFalse(rezervacije.isEmpty());
    }

    @Test
    public void testNadjiRezervaciju() {
        int id = 1;
        Rezervacija rezervacija = rezervacijaManager.nadjiRezervaciju(id);
        assertNotNull(rezervacija);
    }

    @Test
    public void testObrisiRezervaciju() {
    	int id = 2;
        rezervacijaManager.obrisiRezervaciju(id);
        Rezervacija rezervacija = rezervacijaManager.nadjiRezervaciju(id);
        assertNull(rezervacija);
    }
    
    @Test
    public void testPromeniStatusRezervacije() {
        int id = 1; 
        StatusRezervacije noviStatus = StatusRezervacije.POTVRĐENA;
        rezervacijaManager.promeniStatusRezervacije(id, noviStatus);
        
        Rezervacija rezervacija = rezervacijaManager.nadjiRezervaciju(id);
        assertEquals(noviStatus, rezervacija.getStatusRezervacije());
    }

    @Test
    public void testIzracunajUkupanTrosakZaGosta() {
        int ukupanTrosak = rezervacijaManager.izracunajUkupanTrosakZaGosta();
        assertTrue(ukupanTrosak >= 0);
    }
    
    @Test
    public void testPronadjiSlobodneTipoveSoba() {
        LocalDate pocetak = LocalDate.now();
        LocalDate kraj = LocalDate.now().plusDays(5);
        
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        Rezervacija rezervacija = new Rezervacija(1, new Gost("Milica", "Milic", Pol.Z, LocalDate.parse("12.02.1980.", format), "063128416", "Fruskogorska 12", "milica@gmail.com", "002415874"), pocetak, kraj, new TipSobe("jednokrevetna (1)", 1, 1), 1, new ArrayList<>(), 0, new ArrayList<>());
        rezervacija.setStatusRezervacije(StatusRezervacije.POTVRĐENA);
        rezervacijaManager.getRezervacije().add(rezervacija);

        HashMap<String, ArrayList<ArrayList<Oprema>>> slobodniTipovi = rezervacijaManager.pronadjiSlobodneTipoveSoba(pocetak, kraj);
        assertFalse(slobodniTipovi.containsKey("jednokrevetna (1)"));
    }

}
