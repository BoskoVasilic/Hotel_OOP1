package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Oprema;
import manage.OpremaManager;

public class OpremaManagerTest {
	private OpremaManager opremaManager;
	private File opremaFile;

	@Before
	public void setUp() {
		opremaFile = new File("data/test/oprema.csv");
		opremaManager = new OpremaManager(opremaFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		opremaFile.delete();
	}
	

	@Test
    public void testNadjiOpremu() {
        opremaManager.dodajOpremu("oprema1");

        Oprema oprema = opremaManager.nadjiOpremu("oprema1");
        assertNotNull(oprema);
        assertEquals("oprema1", oprema.getNaziv());

        Oprema nePostojecaOprema = opremaManager.nadjiOpremu("nepostojeca");
        assertNull(nePostojecaOprema);
    }

    @Test
    public void testDodajOpremu() {
        boolean rezultat = opremaManager.dodajOpremu("oprema1");
        assertTrue(rezultat);

        ArrayList<Oprema> oprema = opremaManager.getOprema();
        assertEquals(1, oprema.size());
        assertEquals("oprema1", oprema.get(0).getNaziv());

        boolean duplikat = opremaManager.dodajOpremu("oprema1");
        assertFalse(duplikat);
    }

    @Test
    public void testIzmeniOpremu() {
        opremaManager.dodajOpremu("oprema1");

        boolean rezultat = opremaManager.izmeniOpremu("oprema1", "novaOprema");
        assertTrue(rezultat);

        Oprema izmenjenaOprema = opremaManager.nadjiOpremu("novaOprema");
        assertNotNull(izmenjenaOprema);
        assertEquals("novaOprema", izmenjenaOprema.getNaziv());

        boolean nepostojeca = opremaManager.izmeniOpremu("nepostojeca", "novaOprema");
        assertFalse(nepostojeca);
    }

    @Test
    public void testIzbrisiOpremu() {
        opremaManager.dodajOpremu("oprema1");

        boolean rezultat = opremaManager.izbrisiOpremu("oprema1");
        assertTrue(rezultat);

        Oprema obrisanaOprema = opremaManager.nadjiOpremu("oprema1");
        assertNull(obrisanaOprema);

        boolean nepostojeca = opremaManager.izbrisiOpremu("nepostojeca");
        assertFalse(nepostojeca);
    }

}
