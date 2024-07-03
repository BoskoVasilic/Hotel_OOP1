package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdministratorManagerTest.class, CenovnikManagerTest.class, DodatnaUslugaManagerTest.class,
		GostManagerTest.class, OpremaManagerTest.class, OsnovicaManagerTest.class, RecepcionerManagerTest.class,
		RezervacijaManagerTest.class, SobaManagerTest.class, SobaricaManagerTest.class, TipSobeManagerTest.class,
		ZaposleniManagerTest.class })
public class AllTests {

}
