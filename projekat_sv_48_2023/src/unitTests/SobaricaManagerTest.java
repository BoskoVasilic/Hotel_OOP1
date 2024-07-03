package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import entity.Oprema;
import entity.Pol;
import entity.Soba;
import entity.Sobarica;
import entity.StatusSobe;
import entity.StrucnaSprema;
import entity.TipSobe;
import manage.SobaManager;
import manage.SobaricaManager;

public class SobaricaManagerTest {
	private SobaricaManager sm;
	private File sobaFile;
	private SobaManager sbm;

	@Before
	public void setUp() {
		sm = SobaricaManager.getInstance();
		sm.dodajSobaricu("Jovana", "Jovanovic", Pol.M, LocalDate.of(1995, 8, 20), "06444476543", "Adresa 4", "jovana", "lozinka", StrucnaSprema.V, 6);
		sobaFile = new File("data/test/sobe.csv");
		sbm = new SobaManager(sobaFile.getAbsolutePath());
		TipSobe tipSobe = new TipSobe("Jednokrevetna (1)", 1, 1);
        StatusSobe statusSobe = StatusSobe.SLOBODNA;
        ArrayList<Oprema> oprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Klima")));
        boolean pusacka = false;
        sbm.dodajSobu(101, tipSobe, statusSobe, oprema, pusacka);
	}

	@Test
    public void testNadjiSobaricu() {
        Sobarica sobarica = sm.nadjiSobaricu("jovana");
        assertNotNull(sobarica);
    }

    @Test
    public void testDodajSobaricu() {
    	int sobariceSize = sm.getSobarice().size();
        assertTrue(sm.dodajSobaricu("Petar", "Petrovic", Pol.M, LocalDate.of(1995, 8, 20),
                "0649876543", "Adresa 4", "petar", "lozinka", StrucnaSprema.V, 3));

        assertEquals(sobariceSize + 1, sm.getSobarice().size());
    }

    @Test
    public void testIzmeniSobaricu() {
        assertTrue(sm.izmeniSobaricu("Jovanka", "Jovanovic", Pol.Z, LocalDate.of(1985, 12, 10),
                "0654321098", "Nova adresa", "jovana", "nova_lozinka", StrucnaSprema.VI, 8));

        Sobarica izmenjena = sm.nadjiSobaricu("jovana");
        assertEquals("Jovanka", izmenjena.getIme());
        assertEquals("Nova adresa", izmenjena.getAdresa());
    }

    @Test
    public void testObrisiSobaricu() {
    	sm.dodajSobaricu("Petar", "Petrovic", Pol.M, LocalDate.of(1995, 8, 20), "0649876543", "Adresa 4", "petar", "lozinka", StrucnaSprema.V, 3);
    	int sobariceSize = sm.getSobarice().size();
    	assertTrue(sm.obrisiSobaricu("petar"));
        assertEquals(sobariceSize-1, sm.getSobarice().size());
        assertNull(sm.nadjiSobaricu("petar"));
    }

    @Test
    public void testDodajSobuZaSredjivanje() {
        Soba soba = sbm.nadjiSobu(101);

        assertNotNull(sm.dodajSobuZaSredjivanje(soba));
        Sobarica sobarica = sm.dodajSobuZaSredjivanje(soba);
        assertEquals(1, sm.nadjiSobaricu(sobarica.getKorisnickoIme()).getSobeZaSredjivanje().size());
    }

    @Test
    public void testNadjiSobaricuSaNajmanjeDodeljenihSoba() {
        Sobarica sobarica = sm.nadjiSobaricuSaNajmanjeDodeljenihSoba();
        assertNotNull(sobarica);
    }

}
