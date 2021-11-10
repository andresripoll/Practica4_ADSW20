package es.upm.dit.adsw.practica4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PruebaControlEntradaImpl {

	static long tiempo_t1;
	static long tiempo_t2;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (timeout=1000)
	public void test1(){
		boolean fallo = false;
		ControlEntradaImpl ce = new ControlEntradaImpl();

		  try {
		    ce.salirDeTramo();
		  } catch (RuntimeException e) {
		    fallo = true;
		  }

		  assertTrue(fallo);
	}
	
	@Test (timeout=1001)
	public void test2(){
		ControlEntradaImpl ce = new ControlEntradaImpl();
		Thread t1 = new Thread() {
			public void run() {
				tiempo_t1 = ce.entrarEnTramo();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ce.salirDeTramo();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				tiempo_t2 = ce.entrarEnTramo();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ce.salirDeTramo();
			}
		};
		
		t1.start();
		t2.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(tiempo_t1,-1);
		assertEquals(tiempo_t2, 500, 10);
	}
}
