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

import entity.DodatnaUsluga;
import manage.DodatnaUslugaManager;

public class DodatnaUslugaManagerTest {
	private DodatnaUslugaManager dodatnaUslugaManager;
	private File dodatnaUslugaFile;

	@Before
	public void setUp() {
		dodatnaUslugaFile = new File("data/test/dodatneUsluge.csv");
		dodatnaUslugaManager = new DodatnaUslugaManager(dodatnaUslugaFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		dodatnaUslugaFile.delete();
	}
	
	@Test
    public void testNadjiDodatnuUslugu() {
        dodatnaUslugaManager.dodajDodatnuUslugu("usluga1");

        DodatnaUsluga usluga = dodatnaUslugaManager.nadjiDodatnuUslugu("usluga1");
        assertNotNull(usluga);
        assertEquals("usluga1", usluga.getNaziv());

        DodatnaUsluga nePostojecaUsluga = dodatnaUslugaManager.nadjiDodatnuUslugu("nepostojeca");
        assertNull(nePostojecaUsluga);
    }

    @Test
    public void testDodajDodatnuUslugu() {
        boolean rezultat = dodatnaUslugaManager.dodajDodatnuUslugu("usluga1");
        assertTrue(rezultat);

        ArrayList<DodatnaUsluga> dodatneUsluge = dodatnaUslugaManager.getDodatneUsluge();
        assertEquals(1, dodatneUsluge.size());
        assertEquals("usluga1", dodatneUsluge.get(0).getNaziv());

        boolean duplikat = dodatnaUslugaManager.dodajDodatnuUslugu("usluga1");
        assertFalse(duplikat);
    }

    @Test
    public void testIzmeniDodatnuUslugu() {
        dodatnaUslugaManager.dodajDodatnuUslugu("usluga1");

        boolean rezultat = dodatnaUslugaManager.izmeniDodatnuUslugu("usluga1", "novaUsluga1");
        assertTrue(rezultat);

        DodatnaUsluga izmenjenaUsluga = dodatnaUslugaManager.nadjiDodatnuUslugu("novaUsluga1");
        assertNotNull(izmenjenaUsluga);
        assertEquals("novaUsluga1", izmenjenaUsluga.getNaziv());

        boolean nepostojeca = dodatnaUslugaManager.izmeniDodatnuUslugu("nepostojeca", "novaUsluga");
        assertFalse(nepostojeca);
    }

    @Test
    public void testObrisiDodatnuUslugu() {
        dodatnaUslugaManager.dodajDodatnuUslugu("usluga1");

        boolean rezultat = dodatnaUslugaManager.obrisiDodatnuUslugu("usluga1");
        assertTrue(rezultat);

        DodatnaUsluga obrisanaUsluga = dodatnaUslugaManager.nadjiDodatnuUslugu("usluga1");
        assertNull(obrisanaUsluga);

        boolean nepostojeca = dodatnaUslugaManager.obrisiDodatnuUslugu("nepostojeca");
        assertFalse(nepostojeca);
    }

}
