package es.uma.proyecto.ejb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;

public class InformePr {

	private static final Logger LOG = Logger.getLogger(ClientePr.class.getCanonicalName());

	private static final String INFORME_EJB = "java:global/classes/InformeEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionInforme gestionInforme;
	
	@Before
	public void setup() throws NamingException  {
		gestionInforme = (GestionInforme) SuiteTest.ctx.lookup(INFORME_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosTodasHolandaExistente() {
		
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoTodas("ES1113");
			assertEquals(lista.size(),1);
		}catch(CuentaNoExistenteException e) {
			fail("La cuenta segregada si existe");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosTodasHolandaNoExistente() {
			
			try {
				List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoTodas("ES1140");
				fail("Debe saltar excepcion de cuenta segregada no existente");
			}catch(CuentaNoExistenteException e) {
				//OK
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");
			}
			
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosInactivasHolandaExistente() {
		
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoInactivas("ES2022");
			assertEquals(lista.size(),1);
		}catch(CuentaNoExistenteException e) {
			fail("La cuenta segregada que esta inactiva si existe");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosInactivasHolandaNoExistente() {
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoInactivas("ES1113");
			fail("La cuenta segregada no esta de baja");
		}catch(CuentaNoExistenteException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosActivasHolandaExistente() {
		
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoActivas("ES1113");
			assertEquals(lista.size(),1);
		}catch(CuentaNoExistenteException e) {
			fail("La cuenta segregada que esta activa si existe");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosActivasHolandaNoExistente() {
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoActivas("ES1114");
			fail("La cuenta segregada no esta activa");
		}catch(CuentaNoExistenteException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testdevolverInformeHolandaClientesExistentes() {
		
		try {
			List<Individual> lista = gestionInforme.devolverInformeHolandaClientes("Jose", "Garcia", Date.valueOf("2022-04-23"), Date.valueOf("2022-04-26"), "Calle Chozuelas");
			assertEquals(1,lista.size());
		}catch(ClienteNoExistenteException e) {
			fail("El cliente si existe");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testdevolverInformeHolandaClientesNoExistente() {
		
		try {
			List<Individual> lista = gestionInforme.devolverInformeHolandaClientes("Alejandro", "Garcia", Date.valueOf("2022-04-23"), Date.valueOf("2022-04-28"), "Calle Chozuelas");
			fail("El cliente no existe");
		}catch(ClienteNoExistenteException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
}
