package es.uma.proyecto.ejb.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.Individual;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class ClientePr {
	private static final Logger LOG = Logger.getLogger(ClientePr.class.getCanonicalName());

	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionCliente gestionCliente;
	
	@Before
	public void setup() throws NamingException  {
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteIndividual() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		List<Cliente> clientes1 = gestionCliente.devolverTodosClientes();
		try {
		gestionCliente.altaClienteIndividual("Juan", "77670017", "F", "activo", new Date("2022-04-23"), null, "Calle Chozuelas" ,"Alora",  29500, "España", "Juanito", "Lopez", null);
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		List<Cliente> clientes2 = gestionCliente.devolverTodosClientes();
		assertEquals(clientes2.size(), clientes1.size()+1);
		
	}
	
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteIndividualExistente() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		try {
		gestionCliente.altaClienteIndividual("Juan", "77670018", "F", "activo", new Date("2022-04-23"), null, "Calle Chozuelas" ,"Alora",  29500, "España", "Jose", "Garcia", null);
		fail("Deberia haber saltado la excepcion porque es un cliete existente");
		}catch(ClienteExistenteException e) {
		//OK	
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		
		
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteIndividualConNoAdmin() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		try {
		gestionCliente.altaClienteIndividual("Juan1", "77670018", "F", "activo", new Date("2022-04-23"), null, "Calle Chozuelas" ,"Alora",  29500, "España", "Jose", "Garcia", null);
		fail("Deberia haber saltado la excepcion porque no es un administrador");
		}catch(UsuarioNoEsAdministrativoException e) {
		//OK	
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		
		
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteEmpresa() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		List<Cliente> clientes1 = gestionCliente.devolverTodosClientes();
		try {
		gestionCliente.altaClienteEmpresa("Juan", "8885", "J", "activo", new Date("2022-04-23"), null, "Calle Chozuelas" ,"Alora",  29500, "España", "Burger King");
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		List<Cliente> clientes2 = gestionCliente.devolverTodosClientes();
		assertEquals(clientes2.size(), clientes1.size()+1);
		
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteEmpresaExistente() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		try {
			gestionCliente.altaClienteEmpresa("Juan", "8888", "J", "activo", new Date("2022-04-23"), null, "Boulevard Pasteur" ,"Malaga",  29010, "España", "Pilotech");
		fail("Deberia haber saltado la excepcion porque es un cliete existente");
		}catch(ClienteExistenteException e) {
		//OK	
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		
		
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteEmpresaConNoAdmin() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		try {
			gestionCliente.altaClienteEmpresa("Juan1", "8888", "J", "activo", new Date("2022-04-23"), null, "Boulevard Pasteur" ,"Malaga",  29010, "España", "Pilotech");
		fail("Deberia haber saltado la excepcion porque el que lo introduce no es un admin");
		}catch(UsuarioNoEsAdministrativoException e) {
		//OK	
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		
		
	}
	
	@Requisitos({"RF3"})
	@Test
	public void testBajaClienteNoExistente() throws ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		
		try {
			
			
			gestionCliente.bajaCliente("Juan", "77670018");
			}catch(ClienteNoExistenteException e) {
			fail("Este cliente no existe");	
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	@Requisitos({"RF3"})
	@Test
	public void testBajaClienteYaDeBaja() throws ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		
		try {
			
			gestionCliente.bajaCliente("Juan", "77670010");
			fail("El cliente ya estaba de baja");	
			}catch(ClienteYaDeBajaException e) {
			//OK
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	
	
	
}
