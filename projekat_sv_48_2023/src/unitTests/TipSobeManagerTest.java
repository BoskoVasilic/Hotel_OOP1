package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.TipSobe;
import manage.TipSobeManager;

public class TipSobeManagerTest {
	private TipSobeManager tsm;
	private File tipSobeFile;

	@Before
	public void setUp() {
		tipSobeFile = new File("data/test/tipoviSoba.csv");
		tsm = new TipSobeManager(tipSobeFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		tipSobeFile.delete();
	}

	@Test
    public void testNadjiTipSobe() {
        tsm.dodajTipSobe("Standard", 1, 2);
        TipSobe ts = tsm.nadjiTipSobe("Standard");
        assertNotNull(ts);
        assertEquals("Standard", ts.getNaziv());
    }

    @Test
    public void testDodajTipSobe() {
        boolean rezultat = tsm.dodajTipSobe("Deluxe", 1, 2);
        assertTrue(rezultat);
        assertEquals(1, tsm.getTipoviSoba().size());
    }

    @Test
    public void testIzmeniTipSobe() {
        tsm.dodajTipSobe("Suite", 3, 5);
        tsm.izmeniTipSobe("Suite", 4, 6);
        TipSobe ts = tsm.nadjiTipSobe("Suite");
        assertEquals(4, ts.getBrojKreveta());
        assertEquals(6, ts.getBrojOsoba());
    }

    @Test
    public void testObrisiTipSobe() {
        tsm.dodajTipSobe("Economy", 1, 1);
        tsm.obrisiTipSobe("Economy");
        TipSobe ts = tsm.nadjiTipSobe("Economy");
        assertNull(ts);
    }

    @Test
    public void testMaxBrojLjudi() {
        tsm.dodajTipSobe("Family", 3, 4);
        tsm.dodajTipSobe("Single", 1, 1);
        int maxBroj = tsm.maxBrojLjudi();
        assertEquals(4, maxBroj);
    }

}
