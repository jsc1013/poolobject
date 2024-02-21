/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Victor Manuel Vaquero Mesa
 * @author Juan Jose Santos Cambra
 *
 */
public class ReusablePoolTest {
	
	private ReusablePool pool;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		pool = ReusablePool.getInstance();
		// Comprobacion de que el objeto no es nulo
		assertNotNull(pool);
		// Comprobacion de si el objeto es instancia de ReusablePool
		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		try {
			Reusable reusable = null;
			do {
				// Adquirimos un reusable del pool
				reusable = pool.acquireReusable();
				// Comprobacion de que el objeto no es nulo
				assertNotNull(reusable);
				// Comprobacion de si el objeto es instancia de ReusablePool
				assertTrue(reusable instanceof Reusable);
			} while (reusable != null);
		} catch (NotFreeInstanceException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		try {
			Reusable initialReusable = pool.acquireReusable();
			assertNotNull(initialReusable);
			assertTrue(initialReusable instanceof Reusable);

			do {
				pool.releaseReusable(initialReusable);
				Reusable newReusable = pool.acquireReusable();
				assertTrue(initialReusable.util().equals(newReusable.util()));
				assertNotNull(initialReusable);
				assertTrue(initialReusable instanceof Reusable);	
				pool.releaseReusable(newReusable);	
				assertNotNull(newReusable);
				assertTrue(newReusable instanceof Reusable);
			} while (initialReusable != null);

		} catch (NotFreeInstanceException | DuplicatedInstanceException e) {
			System.err.println(e.getMessage());
		}
	}

}
