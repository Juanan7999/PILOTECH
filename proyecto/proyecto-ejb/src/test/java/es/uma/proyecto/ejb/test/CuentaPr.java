package es.uma.proyecto.ejb.test;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;

public class CuentaPr {

	
	private static final Logger LOG = Logger.getLogger(CuentaPr.class.getCanonicalName());

	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionCuenta gestionCuenta;
	
	@Before
	public void setup() throws NamingException  {
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	
	
	@Test
	public void testAbrirCuentaFintechSegregada() {
		
	}
	
	
	@Test
	public void testAbrirCuentaFintechPooled() {
		
	}
	
	
	@Test
	public void testAnadirAutorizados() {
		
	}
	
	@Test
	public void testModificarAutorizados() {
		
	}
	
	
	
}
