package es.uma.proyecto.ejb.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
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
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670020");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		try {
		gestionCliente.altaClienteIndividual("Juan", nuevo_cliente);
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		List<Cliente> clientes2 = gestionCliente.devolverTodosClientes();
		assertEquals(clientes2.size(), clientes1.size()+1);
		
	}
	
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteIndividualExistente() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670010");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		
		
		
		try {
		gestionCliente.altaClienteIndividual("Juan", nuevo_cliente);
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
		
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670020");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		
		
		try {
		gestionCliente.altaClienteIndividual("Juan1", nuevo_cliente);
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
		
		Empresa nueva_empresa = new Empresa();
		nueva_empresa.setIdentificacion("8880");
		nueva_empresa.setTipoCliente("J");
		nueva_empresa.setEstado("activo");
		nueva_empresa.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nueva_empresa.setFechaBaja(null);
		
		
		nueva_empresa.setDireccion("Boulevard Pasteur");
		nueva_empresa.setCiudad("Malaga");
		nueva_empresa.setCodigopostal(29010);
		nueva_empresa.setPais("España");
		
		nueva_empresa.setRazonSocial("Pilotech");
		
		
		try {
		gestionCliente.altaClienteEmpresa("Juan", nueva_empresa);
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		List<Cliente> clientes2 = gestionCliente.devolverTodosClientes();
		assertEquals(clientes2.size(), clientes1.size()+1);
		
	}
	
	@Requisitos({"RF2"})
	@Test
	public void testAltaClienteEmpresaExistente() throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		
		Empresa nueva_empresa = new Empresa();
		nueva_empresa.setIdentificacion("8888");
		nueva_empresa.setTipoCliente("J");
		nueva_empresa.setEstado("activo");
		nueva_empresa.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nueva_empresa.setFechaBaja(null);
		
		
		nueva_empresa.setDireccion("Boulevard Pasteur");
		nueva_empresa.setCiudad("Malaga");
		nueva_empresa.setCodigopostal(29010);
		nueva_empresa.setPais("España");
		
		nueva_empresa.setRazonSocial("Pilotech");
		
		
		try {
			gestionCliente.altaClienteEmpresa("Juan", nueva_empresa);
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
		
		Empresa nueva_empresa = new Empresa();
		nueva_empresa.setIdentificacion("8880");
		nueva_empresa.setTipoCliente("J");
		nueva_empresa.setEstado("activo");
		nueva_empresa.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nueva_empresa.setFechaBaja(null);
		
		
		nueva_empresa.setDireccion("Boulevard Pasteur");
		nueva_empresa.setCiudad("Malaga");
		nueva_empresa.setCodigopostal(29010);
		nueva_empresa.setPais("España");
		
		nueva_empresa.setRazonSocial("Pilotech");
		
		
		
		try {
			gestionCliente.altaClienteEmpresa("Juan1", nueva_empresa);
		fail("Deberia haber saltado la excepcion porque el que lo introduce no es un admin");
		}catch(UsuarioNoEsAdministrativoException e) {
		//OK	
		}catch(ProyectoEjbException e) {
		fail("Excepcion inesperada");	
		}
		
		
	}
	
	@Requisitos({"RF4"})
	@Test
	public void testBajaClienteNoExistente() throws ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		
		
		
		
		
		try {
			
			
			gestionCliente.bajaCliente("Juan", "77670032");
			fail("Deberia saltar excepcion de que este cliente no existe");
			}catch(ClienteNoExistenteException e) {
			//OK	
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	@Requisitos({"RF4"})
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
	
	@Requisitos({"RF4"})
	@Test
	public void testBajaClienteConNoAdmin() throws ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		
		try {
			
			gestionCliente.bajaCliente("Juan1", "77670018");
			fail("El cliente ya estaba de baja");	
			}catch(UsuarioNoEsAdministrativoException e) {
			//OK
			}catch(ProyectoEjbException e) {
				fail("Excepcion inesperada");	
			}
	}
	
	@Requisitos({"RF3"})
	@Test
	public void testModificarDatosClienteIndividualNoExistente() {
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670020");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		
		try {
			gestionCliente.modificarDatosClienteIndividual("Juan", "77670004", nuevo_cliente);
			fail("Debería saltar excepcion de cliente no existente");
		}catch(ClienteNoExistenteException e) {
			//OK
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	
	@Requisitos({"RF3"})
	@Test
	public void testModificarDatosClienteIndividualConNoAdmin() {
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670018");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		
		
		try {
			gestionCliente.modificarDatosClienteIndividual("Juan1", "77670018", nuevo_cliente);
			fail("Debería saltar excepcion de que el usuario no es administrativo");
		}catch(UsuarioNoEsAdministrativoException e) {
			//Ok
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	
	@Requisitos({"RF3"})
	@Test
	public void testModificarDatosClienteEmpresaNoExistente() {
		
		Empresa nueva_empresa = new Empresa();
		nueva_empresa.setIdentificacion("8882");
		nueva_empresa.setTipoCliente("J");
		nueva_empresa.setEstado("activo");
		nueva_empresa.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nueva_empresa.setFechaBaja(null);
		
		
		nueva_empresa.setDireccion("Boulevard Pasteur");
		nueva_empresa.setCiudad("Malaga");
		nueva_empresa.setCodigopostal(29010);
		nueva_empresa.setPais("España");
		
		nueva_empresa.setRazonSocial("Pilotech");
		
		
		try {
			gestionCliente.modificarDatosClienteEmpresa("Juan", "8887", nueva_empresa);
			fail("Debería saltar excepcion de cliente no existente");
		}catch(ClienteNoExistenteException e) {
			//OK
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	

	
	
	@Requisitos({"RF16"})
	@Test
	public void testActivaClienteNoExistente() {
		
		
		
		try {
			gestionCliente.activaCliente("Juan", "123");
			fail("Debe saltar excepcion de que el cliente no existe");
		}catch(ClienteNoExistenteException e) {
			//Ok
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	

	
	@Requisitos({"RF16"})
	@Test
	public void testActivaClienteYaActivo() {
		
		
		try {
			gestionCliente.activaCliente("Juan", "77670018");
			fail("Debe saltar excepcion de que el cliente ya está bloqueado");
		}catch(ClienteYaActivoException e) {
			//Ok
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	
	@Requisitos({"RF16"})
	@Test
	public void testBloqueaClienteNoExistente() {
		
		
		
		
		try {
			gestionCliente.bloqueaCliente("Juan", "123");
			fail("Debe saltar excepcion de que el cliente no existe");
		}catch(ClienteNoExistenteException e) {
			//Ok
			
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	

	
	@Requisitos({"RF16"})
	@Test
	public void testBloqueaClienteYaBloqueado() {
		
		
		
		try {
			gestionCliente.bloqueaCliente("Juan", "77670011");
			fail("Debe saltar excepcion de que el cliente ya esta bloqueado");
		}catch(ClienteBloqueadoException e) {
			//Ok
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");	
		}
	}
	
}
