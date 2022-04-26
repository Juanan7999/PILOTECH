package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteDesbloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Stateless
public class ClienteEJB implements GestionCliente{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void altaClienteIndividual(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		Individual clienteIndividualEntity = em.find(Individual.class, identificacion);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
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
	public void altaClienteEmpresa(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social) throws ClienteExistenteException, UsuarioNoEsAdministrativoException{
		Empresa clienteEmpresaEntity = em.find(Empresa.class, identificacion);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
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
		
		em.persist(nuevo_cliente);
	}

	@Override
	public void bajaCliente(String idAdmin, Cliente cliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		Cliente clienteEntity = em.find(Cliente.class, cliente);
		
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if(clienteEntity.getEstado().equals("baja")) {
			throw new ClienteYaDeBajaException();
		} 
		
		List<CuentaFintech> cuentas = clienteEntity.getCuentaFinteches();
		
		for(CuentaFintech c : cuentas) {
			if(c.getEstado().equals("activa")) {
				throw new CuentaAbiertaException();
			}
		}
		
		clienteEntity.setEstado("baja");
	}

	@Override
	public void activaCliente(String idAdmin, Cliente cliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Cliente clienteEntity = em.find(Cliente.class, cliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteYaActivoException();
		}
		
		clienteEntity.setEstado("activo");
		
	}

	@Override
	public void bloqueaCliente(String idAdmin, Cliente cliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		
		Cliente clienteEntity = em.find(Cliente.class,cliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteBloqueadoException();
		}
		
		clienteEntity.setEstado("bloqueado");
		
		
	}
	
	@Override
	public List<Cliente> devolverTodosClientes(){
		TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> clientes= query.getResultList();
		return clientes;
	}

	
	
	
}
