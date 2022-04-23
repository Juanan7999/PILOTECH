package es.uma.proyecto.ejb.test;

import static org.junit.Assert.fail;


import java.util.Date;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.*;


public class UsuarioPr {

	private static final Logger LOG = Logger.getLogger(UsuarioPr.class.getCanonicalName());

	private static final String USUARIO_EJB = "java:global/classes/UsuarioEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionUsuario gestionUsuario;
	
	@Before
	public void setup() throws NamingException  {
		gestionUsuario = (GestionUsuario) SuiteTest.ctx.lookup(USUARIO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	public void testCreacionUsuarioExistente() throws ProyectoEjbException {
		
		final String nombreUsuario = "Juan";
		final String password = "1234";
		final String tipo = "N";
		
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario("Juan");
		usuario.setPassword("1234");
		usuario.setTipo("A");
		
		/*Cliente cliente = new Cliente();
		cliente.setIdentificacion("1111");
		cliente.setTipoCliente("F");
		cliente.setPais("España");
		cliente.setEstado("activo");
		cliente.setFechaAlta();*/
		
		try {
			gestionUsuario.creacionUsuario(nombreUsuario, password, tipo, null, null);
			fail("Debe lanzar excepcion");
		} catch(UsuarioExistenteException e) {
			//OK
		} catch(ProyectoEjbException e) {
			fail("Debe lanzar la excepcion de que el usuario ya existia antes");
		}
	}
	
	
	
	@Test
	public void LoginUsuarioExistente() throws UsuarioNoEncontradoException, ContraseñaIncorrectaException{
		//No deberia saltar error, porque ese usuario existe
		
		final String nombreUsuario = "Juan";
		final String password = "8234";
		
		
		try {
		gestionUsuario.Login(nombreUsuario, password);
		}catch(UsuarioNoEncontradoException e){
		fail("El usuario sí existe");
		}
		
		
	}
	
	
	
	
	@Test
	public void LoginUsuarioNoExistente() throws UsuarioNoEncontradoException, ContraseñaIncorrectaException{
		//No deberia saltar error, porque ese usuario existe
		
		final String nombreUsuario = "JuanFalso";
		final String password = "8234";
		
		
		try {
		gestionUsuario.Login(nombreUsuario, password);
		}catch(UsuarioNoEncontradoException e){
		//OK
		}
		fail("El usuario sí existe");
		
	}
	
	@Test
	public void LoginPasswordIncorrecta() throws UsuarioNoEncontradoException, ContraseñaIncorrectaException{
		//No deberia saltar error, porque ese usuario existe
		
		final String nombreUsuario = "Juan"; //Usuario que existe
		final String password = "8333"; //Contraseña incorrecta
		
		
		try {
		gestionUsuario.Login(nombreUsuario, password);
		}catch(ContraseñaIncorrectaException e){
		//OK
		}
		fail("La contraseña no es correcta, deberia saltar la excepcion");
		
	}
	
	@Test
	public void LoginPasswordCorrecta() throws UsuarioNoEncontradoException, ContraseñaIncorrectaException{
		//No deberia saltar error, porque ese usuario existe
		
		final String nombreUsuario = "Juan"; //Usuario que existe
		final String password = "8333"; //Contraseña incorrecta
		
		
		try {
		gestionUsuario.Login(nombreUsuario, password);
		}catch(ContraseñaIncorrectaException e){
			fail("La contraseña es correcta, no deberia saltar la excepcion");
		}
		//OK
		
	}
}
