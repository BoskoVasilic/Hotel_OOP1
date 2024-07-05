package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Oprema;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;
import manage.SobaManager;

public class SobaManagerTest {
	private SobaManager sm;
	private File sobaFile;

	@Before
	public void setUp() {
		sobaFile = new File("data/test/sobe.csv");
		sm = new SobaManager(sobaFile.getAbsolutePath());
		TipSobe tipSobe = new TipSobe("Jednokrevetna (1)", 1, 1);
        StatusSobe statusSobe = StatusSobe.SLOBODNA;
        ArrayList<Oprema> oprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Klima")));
        boolean pusacka = false;
        sm.dodajSobu(101, tipSobe, statusSobe, oprema, pusacka);
	}

	@After
	public void tearDown() {
		sobaFile.delete();
	}

    @Test
    public void testNadjiSobu() {
        Soba soba = sm.nadjiSobu(101);
        assertNotNull(soba);

        soba = sm.nadjiSobu(999);
        assertNull(soba); 
    }

    @Test
    public void testDodajSobu() {
        TipSobe tipSobe = new TipSobe("Jednokrevetna (1)", 1, 1);
        StatusSobe statusSobe = StatusSobe.SLOBODNA;
        ArrayList<Oprema> oprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Klima")));
        boolean pusacka = false;

        boolean rezultat = sm.dodajSobu(104, tipSobe, statusSobe, oprema, pusacka);
        assertTrue(rezultat);
    }

    @Test
    public void testIzmeniSobu() {
        Soba soba = sm.nadjiSobu(101);
        assertNotNull(soba);

        TipSobe noviTip = new TipSobe("Dvokrevetna (2)", 1, 2);
        StatusSobe noviStatus = StatusSobe.ZAUZETA;
        ArrayList<Oprema> novaOprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Mini-bar")));
        boolean novaPusacka = true;

        boolean rezultat = sm.izmeniSobu(101, noviTip, noviStatus, novaOprema, novaPusacka);
        assertTrue(rezultat);

        Soba izmenjenaSoba = sm.nadjiSobu(101);
        assertEquals(noviTip, izmenjenaSoba.getTipSobe());
        assertEquals(noviStatus, izmenjenaSoba.getStatusSobe());
        assertEquals(novaOprema, izmenjenaSoba.getOpremljenostSobe());
        assertEquals(novaPusacka, izmenjenaSoba.isPusackaSoba());
    }

    @Test
    public void testObrisiSobu() {
        boolean rezultat = sm.obrisiSobu(101);
        assertTrue(rezultat);

        rezultat = sm.obrisiSobu(999);
        assertFalse(rezultat);
    }

    @Test
    public void testGetBrojSobaPoTipu() {
    	TipSobe tipSobe = new TipSobe("Jednokrevetna (1)", 1, 1);
        StatusSobe statusSobe = StatusSobe.SLOBODNA;
        ArrayList<Oprema> oprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Klima")));
        boolean pusacka = false;
        sm.dodajSobu(105, tipSobe, statusSobe, oprema, pusacka);
        
        HashMap<String, Integer> brojSobaPoTipu = sm.getBrojSobaPoTipu();
        assertEquals(2, (int) brojSobaPoTipu.get("Jednokrevetna (1)"));

    }

    @Test
    public void testGetSlobodneSobeTipa() {
        TipSobe tip = new TipSobe("Jednokrevetna (1)", 1, 1);
        ArrayList<Oprema> zahtevanaOprema = new ArrayList<>(Arrays.asList(new Oprema("TV")));

        ArrayList<String> slobodneSobe = sm.getSlobodneSobeTipa(tip, zahtevanaOprema);
        assertEquals(1, slobodneSobe.size());
        assertTrue(slobodneSobe.contains("101"));
    }

    @Test
    public void testGetOpremaPoTipuSobe() {
    	TipSobe tipSobe = new TipSobe("Jednokrevetna (1)", 1, 1);
        StatusSobe statusSobe = StatusSobe.SLOBODNA;
        ArrayList<Oprema> oprema = new ArrayList<>(Arrays.asList(new Oprema("TV"), new Oprema("Klima")));
        boolean pusacka = false;
        sm.dodajSobu(105, tipSobe, statusSobe, oprema, pusacka);
        
        HashMap<String, ArrayList<ArrayList<Oprema>>> opremaPoTipu = sm.getOpremaPoTipuSobe();
        assertEquals(2, opremaPoTipu.get("Jednokrevetna (1)").size());
    }

}
