package es.uma.proyectoEjbTest;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Usuario;
import es.uma.proyectoEjb.GestionUsuario;
import es.uma.proyectoEjb.exceptions.ProyectoEjbException;
import es.uma.proyectoEjb.exceptions.UsuarioExistenteException;


public class UsuarioTest {

	private static final Logger LOG = Logger.getLogger(UsuarioTest.class.getCanonicalName());

	private static final String USUARIO_EJB = "java:global/classes/UsuarioEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb";
	
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
		} catch(UsuarioExistenteException e) {
			//OK
		}
		fail("No se ha lanzado la excepcion de que el usuario ya existia antes");
	}
}
