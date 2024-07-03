package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import entity.Osnovica;
import entity.Pozicija;
import manage.OsnovicaManager;

public class OsnovicaManagerTest {
	private OsnovicaManager osnovicaManager;

	@Before
	public void setUp() {
		osnovicaManager = OsnovicaManager.getInstance();
	}

	@Test
	public void testNadjiOsnovicu() {
		osnovicaManager.dodajOsnovicu(100, Pozicija.Recepcioner);

        Osnovica osnovica = osnovicaManager.nadjiOsnovicu(Pozicija.Recepcioner);
        assertNotNull(osnovica);
    }
	
	@Test
	public void testDodajOsnovicu() {
		int osnoviceSize = osnovicaManager.getOsnovica().size();
		boolean rezultat = osnovicaManager.dodajOsnovicu(100, Pozicija.Recepcioner);
		assertEquals(true, rezultat);

		assertEquals(osnoviceSize + 1, osnovicaManager.getOsnovica().size());
	}
		
}
