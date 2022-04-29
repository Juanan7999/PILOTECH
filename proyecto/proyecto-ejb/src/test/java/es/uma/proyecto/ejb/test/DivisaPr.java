package es.uma.proyecto.ejb.test;

import static org.junit.Assert.fail;

import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionDivisa;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class DivisaPr {

	private static final Logger LOG = Logger.getLogger(DivisaPr.class.getCanonicalName());

	private static final String DIVISA_EJB = "java:global/classes/DivisaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String USUARIO_EJB = "java:global/classes/UsuarioEJB";
	
	private GestionDivisa gestionDivisa;
	private GestionCliente gestionCliente;
	private GestionUsuario gestionUsuario;
	
	@PersistenceContext(name="proyecto-ejbTest")
	private EntityManager em;
	@Before
	public void setup() throws NamingException  {
		gestionDivisa = (GestionDivisa) SuiteTest.ctx.lookup(DIVISA_EJB);
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		gestionUsuario = (GestionUsuario) SuiteTest.ctx.lookup(USUARIO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	
	@Test
	public void testCambioDivisaClientePersAutorizadaNoEncontrada() {
		
		PooledAccount p = em.find(PooledAccount.class, "ES1112");
		CuentaReferencia c1 = em.find(CuentaReferencia.class, "ES1111");
		CuentaReferencia c2 = em.find(CuentaReferencia.class, "ES1114");
		Transaccion t = em.find(Transaccion.class,1234);
		
		try {
			Usuario pa = gestionUsuario.Login("Juan", "8230");
			gestionDivisa.cambioDeDivisaCliente_Autorizado(null, p, c1, c2 , 30.0 , t);
			fail("Debe saltar exception de que el cliente no existe");
		}catch(ClientePersonaAutorizadaNoEncontradoException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	
	
	@Test
	public void testCambioDivisaClientePersAutorizadaPooledNoExistente() {
		
		PooledAccount p = em.find(PooledAccount.class, "ES1112");
		CuentaReferencia c1 = em.find(CuentaReferencia.class, "ES1111");
		CuentaReferencia c2 = em.find(CuentaReferencia.class, "ES1114");
		Transaccion t = em.find(Transaccion.class,1234);
		
		try {
			Usuario pa = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaCliente_Autorizado(null, null, null, null, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		}catch(PooledNoExistenteException e) {
			//OK
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	
	
	@Test
	public void testCambioDivisaClientePersAutorizadaCuentasDiferentes() {
		
		PooledAccount p = em.find(PooledAccount.class, "ES1112");
		CuentaReferencia c1 = em.find(CuentaReferencia.class, "ES1111");
		CuentaReferencia c2 = em.find(CuentaReferencia.class, "ES1114");
		Transaccion t = em.find(Transaccion.class,1234);
		
		try {
			Usuario pa = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaCliente_Autorizado(null, null, null, null, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		}catch(CuentasDiferentesException e) {
			//OK
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	public void testCambioDivisaAdminUsuarioNoEsAdmin() {
		
		PooledAccount p = em.find(PooledAccount.class, "ES1112");
		CuentaReferencia c1 = em.find(CuentaReferencia.class, "ES1111");
		CuentaReferencia c2 = em.find(CuentaReferencia.class, "ES1114");
		Transaccion t = em.find(Transaccion.class,1234);
		
		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaCliente_Autorizado(null, null, null, null , null , null);
			fail("Debe saltar exception de que el usuario no es admin");
		//}catch(UsuarioNoEsAdministrativoException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	
	
	
}
