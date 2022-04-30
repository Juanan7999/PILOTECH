package es.uma.proyecto.ejb.test;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
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
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
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
	
	@Before
	public void setup() throws NamingException  {
		gestionDivisa = (GestionDivisa) SuiteTest.ctx.lookup(DIVISA_EJB);
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		gestionUsuario = (GestionUsuario) SuiteTest.ctx.lookup(USUARIO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Requisitos({"RF17"})
	@Test
	public void testCambioDivisaClientePersAutorizadaNoEncontrada() {
		
		
		try {
			
			gestionDivisa.cambioDeDivisaCliente_Autorizado("67670001", null, null, null , null, null);
			fail("Debe saltar exception de que el cliente no existe");
		}catch(ClientePersonaAutorizadaNoEncontradoException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	
	@Requisitos({"RF17"})
	@Test
	public void testCambioDivisaClientePersAutorizadaPooledNoExistente() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES2112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
        
		
		
		try {
			
			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, null, null, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		}catch(PooledNoExistenteException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	
	@Requisitos({"RF17"})
	@Test
	public void testCambioDivisaClientePersAutorizadaCuentasDiferentes() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES1112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
        
        CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		
		
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		
        
		
		try {
			
			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, cuentaref, cuentaref2, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		}catch(CuentasDiferentesException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF17"})
	@Test
	public void testCambioDivisiaClientePersAturoizadaSaldoInsuf() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES1112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
        
        CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1111");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		
		try {
			
			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, cuentaref, cuentaref2, 50.0, null);
			fail("Debe saltar exception de que el saldo es insuficiente");
		}catch(SaldoInsuficienteException e) {
			//OK
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	@Requisitos({"RF18"})
	@Test
	public void testCambioDivisaAdminUsuarioNoEsAdmin() {
		
	
		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, null, null, null, null, null, null);
			fail("Debe saltar exception de que el usuario no es admin");
		}catch(UsuarioNoEsAdministrativoException e) {
			//OK
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar excepcion de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF18"})
	@Test
	public void testCambioDivisiaAdminClientePersAutNoEncontrado() {
		
		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin,"67670001", null, null, null , null, null);
			fail("Debe saltar exception de que el cliente no existe");
		}catch(ClientePersonaAutorizadaNoEncontradoException e) {
			//OK
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	@Requisitos({"RF18"})
	@Test
	public void testCambioDivisaAdminPooledNoExistente() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES2112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		
		
		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, null, null, null, null);
			fail("Debe saltar exception de que la pooled no existe");
		}catch(PooledNoExistenteException e) {
			
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF18"})
	@Test
	public void testCambioDivisaAdminCuentasDiferentes() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES1112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
        
        CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		
		
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
        
		
		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, cuentaref, cuentaref2, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		}catch(CuentasDiferentesException e) {
			//OK
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
	@Requisitos({"RF18"})
	@Test
	public void testCambioDivisiaAdminSaldoInsuf() {
		
		PooledAccount pooled = new PooledAccount();
        pooled.setIban("ES1112");
        pooled.setSwift("2346");
        pooled.setEstado("activa");
        pooled.setFechaApertura(Date.valueOf("2022-04-25"));
        
        CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1111");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		
		
		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, cuentaref, cuentaref2, 50.0,null);
			fail("Debe saltar exception de que el saldo es insuficiente");
		}catch(SaldoInsuficienteException e) {
			
		}catch(ContraseñaIncorrectaException e) {
			fail("No deberia saltar excepcion de contraseña incorrecta");
		}catch(ProyectoEjbException e) {
			fail("Exception inesperada");
		}
		
	}
	
	
	
	
	
}
