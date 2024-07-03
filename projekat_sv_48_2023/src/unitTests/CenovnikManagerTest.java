package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Cenovnik;
import manage.CenovnikManager;

public class CenovnikManagerTest {
	private CenovnikManager cenovnikManager;
	private File cenovnikFile;

	@Before
	public void setUp() {
		cenovnikFile = new File("data/test/cenovnici.csv");
		cenovnikManager = new CenovnikManager(cenovnikFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		cenovnikFile.delete();
	}

	@Test
    public void testNadjiCenovnikZaDatum() {
        HashMap<String, HashMap<String, Double>> cene = new HashMap<>();
        cene.put("grupa1", new HashMap<>());
        cene.get("grupa1").put("stavka1", 100.0);

        cenovnikManager.dodajCenovnikGui(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), cene);

        Cenovnik cenovnik = cenovnikManager.nadjiCenovnikZaDatum(LocalDate.of(2023, 6, 15));
        assertNotNull(cenovnik);
        assertEquals(LocalDate.of(2023, 1, 1), cenovnik.getPocetakVazenja());
        assertEquals(LocalDate.of(2023, 12, 31), cenovnik.getKrajVazenja());

        Cenovnik nePostojeciCenovnik = cenovnikManager.nadjiCenovnikZaDatum(LocalDate.of(2022, 6, 15));
        assertNull(nePostojeciCenovnik);
    }

    @Test
    public void testDodajCenovnikGui() {
        HashMap<String, HashMap<String, Double>> cene = new HashMap<>();
        cene.put("grupa1", new HashMap<>());
        cene.get("grupa1").put("stavka1", 100.0);

        boolean rezultat = cenovnikManager.dodajCenovnikGui(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), cene);
        assertTrue(rezultat);

        ArrayList<Cenovnik> cenovnici = cenovnikManager.getCenovnici();
        assertEquals(1, cenovnici.size());

        Cenovnik cenovnik = cenovnici.get(0);
        assertEquals(LocalDate.of(2023, 1, 1), cenovnik.getPocetakVazenja());
        assertEquals(LocalDate.of(2023, 12, 31), cenovnik.getKrajVazenja());
        assertTrue(cenovnik.getCene().containsKey("grupa1"));
        assertEquals(100.0, cenovnik.getCene().get("grupa1").get("stavka1"), 0.01);
    }

    @Test
    public void testIzmeniCenovnikKompletGUI() {
        HashMap<String, HashMap<String, Double>> cene = new HashMap<>();
        cene.put("grupa1", new HashMap<>());
        cene.get("grupa1").put("stavka1", 100.0);

        cenovnikManager.dodajCenovnikGui(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), cene);

        HashMap<String, HashMap<String, Double>> noveCene = new HashMap<>();
        noveCene.put("grupa1", new HashMap<>());
        noveCene.get("grupa1").put("stavka1", 150.0);

        boolean rezultat = cenovnikManager.izmeniCenovnikKompletGUI(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), noveCene);
        assertTrue(rezultat);

        Cenovnik cenovnik = cenovnikManager.nadjiCenovnik(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertNotNull(cenovnik);
        assertEquals(150.0, cenovnik.getCene().get("grupa1").get("stavka1"), 0.01);
    }

    @Test
    public void testObrisiCenovnik() {
        HashMap<String, HashMap<String, Double>> cene = new HashMap<>();
        cene.put("grupa1", new HashMap<>());
        cene.get("grupa1").put("stavka1", 100.0);

        cenovnikManager.dodajCenovnikGui(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), cene);

        boolean rezultat = cenovnikManager.obrisiCenovnik(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertTrue(rezultat);

        ArrayList<Cenovnik> cenovnici = cenovnikManager.getCenovnici();
        assertEquals(0, cenovnici.size());
    }
	

}
