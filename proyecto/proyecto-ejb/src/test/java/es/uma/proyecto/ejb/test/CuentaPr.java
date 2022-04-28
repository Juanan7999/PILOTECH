package es.uma.proyecto.ejb.test;

import static org.junit.Assert.fail;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class CuentaPr {

	
	private static final Logger LOG = Logger.getLogger(CuentaPr.class.getCanonicalName());

	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String USUARIO_EJB = "java:global/classes/UsuarioEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionCuenta gestionCuenta;
	private GestionUsuario gestionUsuario;
	
	@Before
	public void setup() throws NamingException  {
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		gestionUsuario = (GestionUsuario) SuiteTest.ctx.lookup(USUARIO_EJB); 
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	
	
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaFintechSegregadaConNoAdmin() {
	
		
		
		try {
		Usuario admin = gestionUsuario.Login("Jose", "8234");
		gestionCuenta.abrirCuentaFintechSegregada(admin, null , null, null);
		fail("Debería haber saltado excepcion de que no es un administrador");
		}catch(UsuarioNoEsAdministrativoException e) {
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	
	
	@Requisitos({"RF5"})
	@Test
	public void testAbrirCuentaFintechPooledConNoAdmin() {
		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionCuenta.abrirCuentaFintechPooled(admin, null , null, null);
			fail("Debería haber saltado excepcion de que no es un administrador");
			}catch(UsuarioNoEsAdministrativoException e) {
				
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	@Requisitos({"RF6"})
	@Test
	public void testAnadirAutorizadosConNoAdmin() {
		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionCuenta.anadirAutorizados(admin, null , null);
			fail("Debería haber saltado excepcion de que no es un administrador");
			}catch(UsuarioNoEsAdministrativoException e) {
				
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	
	@Requisitos({"RF7"})
	@Test
	public void testModificarAutorizadosConNoAdmin() {
		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionCuenta.modificarAutorizados(admin, null);
			fail("Debería haber saltado excepcion de que no es un administrador");
			}catch(UsuarioNoEsAdministrativoException e) {
				
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	
	@Requisitos({"RF8"})
	@Test
	public void testEliminarAutorizadosConNoAdmin() {
		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionCuenta.eliminarAutorizados(admin, null);
			fail("Debería haber saltado excepcion de que no es un administrador");
			}catch(UsuarioNoEsAdministrativoException e) {
				
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	
	
}
