package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;


@Stateless
public class ClienteEJB implements GestionCliente{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void altaClienteIndividual(String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja,
			String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteExistenteException {
		Individual clienteIndividualEntity = em.find(Individual.class, identificacion);
		
		if(clienteIndividualEntity != null) {
			throw new ClienteExistenteException();
		}
		
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion(identificacion);
		nuevo_cliente.setTipoCliente(tipo);
		nuevo_cliente.setEstado(estado);
		nuevo_cliente.setFechaAlta(fecha_alta);
		
		if(fecha_baja !=null) {
			nuevo_cliente.setFechaBaja(fecha_baja);
		}
		
		nuevo_cliente.setDireccion(direccion);
		nuevo_cliente.setCiudad(ciudad);
		nuevo_cliente.setCodigopostal(codigo_postal);
		nuevo_cliente.setPais(pais);
		
		nuevo_cliente.setNombre(nombre);
		nuevo_cliente.setApellido(apellidos);
		
		if(fecha_nacimiento != null) {
			nuevo_cliente.setFechaNacimiento(fecha_nacimiento);
		}
		
	}

	@Override
	public void altaClienteEmpresa(String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja,
			String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social) throws ClienteExistenteException{
		Empresa clienteEmpresaEntity = em.find(Empresa.class, identificacion);
		
		if(clienteEmpresaEntity != null) {
			throw new ClienteExistenteException();
		}
		
		Empresa nuevo_cliente = new Empresa();
		nuevo_cliente.setIdentificacion(identificacion);
		nuevo_cliente.setTipoCliente(tipo);
		nuevo_cliente.setEstado(estado);
		nuevo_cliente.setFechaAlta(fecha_alta);
		
		if(fecha_baja !=null) {
			nuevo_cliente.setFechaBaja(fecha_baja);
		}
		
		nuevo_cliente.setDireccion(direccion);
		nuevo_cliente.setCiudad(ciudad);
		nuevo_cliente.setCodigopostal(codigo_postal);
		nuevo_cliente.setPais(pais);
		
		nuevo_cliente.setRazonSocial(razon_social);
		
	}

	@Override
	public void bajaCliente(Cliente cliente) throws ClienteNoExistenteException, ClienteYaDeBajaException {
		
		Cliente clienteEntity = em.find(Cliente.class, cliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if(clienteEntity.getEstado().equals("baja")) {
			throw new ClienteYaDeBajaException();
		}
		
		clienteEntity.setEstado("baja");
	}

	@Override
	public void activaCliente(Cliente cliente) throws ClienteNoExistenteException, ClienteYaActivoException {
		
		Cliente clienteEntity = em.find(Cliente.class, cliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteYaActivoException();
		}
		
	}
	
	
}
