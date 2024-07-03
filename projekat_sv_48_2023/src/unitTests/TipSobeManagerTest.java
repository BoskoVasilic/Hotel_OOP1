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
        tsm.dodajTipSobe("Dvokrevetna (2)", 1, 2);
        TipSobe ts = tsm.nadjiTipSobe("Dvokrevetna (2)");
        assertNotNull(ts);
        assertEquals("Dvokrevetna (2)", ts.getNaziv());
    }

    @Test
    public void testDodajTipSobe() {
        boolean rezultat = tsm.dodajTipSobe("Dvokrevetna (2)", 1, 2);
        assertTrue(rezultat);
        assertEquals(1, tsm.getTipoviSoba().size());
    }

    @Test
    public void testIzmeniTipSobe() {
        tsm.dodajTipSobe("Trokrevetna (3)", 3, 5);
        tsm.izmeniTipSobe("Trokrevetna (3)", 4, 6);
        TipSobe ts = tsm.nadjiTipSobe("Trokrevetna (3)");
        assertEquals(4, ts.getBrojKreveta());
        assertEquals(6, ts.getBrojOsoba());
    }

    @Test
    public void testObrisiTipSobe() {
        tsm.dodajTipSobe("Jednokrevetna (1)", 1, 1);
        tsm.obrisiTipSobe("Jednokrevetna (1)");
        TipSobe ts = tsm.nadjiTipSobe("Jednokrevetna (1)");
        assertNull(ts);
    }

    @Test
    public void testMaxBrojLjudi() {
        tsm.dodajTipSobe("Trokrevetna (3)", 3, 4);
        tsm.dodajTipSobe("Jednokrevetna (1)", 1, 1);
        int maxBroj = tsm.maxBrojLjudi();
        assertEquals(4, maxBroj);
    }

}
