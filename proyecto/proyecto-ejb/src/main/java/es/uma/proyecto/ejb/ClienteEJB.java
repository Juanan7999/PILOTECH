package es.uma.proyecto.ejb;

import java.sql.Date;
import java.util.ArrayList;
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
	public void altaClienteIndividual(String idAdmin, Cliente individual) throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		Individual clienteIndividualEntity = em.find(Individual.class, individual);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteIndividualEntity != null) {
			throw new ClienteExistenteException();
		}
		
		em.persist(individual);
		 
	}

	@Override
	public void altaClienteEmpresa(String idAdmin, Cliente empresa) throws ClienteExistenteException, UsuarioNoEsAdministrativoException{
		Empresa clienteEmpresaEntity = em.find(Empresa.class, empresa);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteEmpresaEntity != null) {
			throw new ClienteExistenteException();
		}
				
		em.persist(empresa);
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
		} else if (clienteEntity.getEstado().endsWith("bloqueado")) {
			throw new ClienteBloqueadoException();
		}
		
		clienteEntity.setEstado("bloqueado");
		
		
	}
	
	@Override
	public void modificarDatosClienteIndividual(String idAdmin, String identificacionCliente, Individual individual) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Individual clienteEntity = em.find(Individual.class,identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		
			clienteEntity.setIdentificacion(individual.getIdentificacion());
		
			clienteEntity.setTipoCliente(individual.getTipoCliente());
	
			clienteEntity.setEstado(individual.getEstado());
	
			clienteEntity.setFechaAlta(individual.getFechaAlta());
		
			clienteEntity.setFechaBaja(individual.getFechaBaja());
		
			clienteEntity.setDireccion(individual.getDireccion());
		
			clienteEntity.setCiudad(individual.getCiudad());
	
			clienteEntity.setCodigopostal(individual.getCodigopostal());
		
			clienteEntity.setPais(individual.getPais());
	
			clienteEntity.setNombre(individual.getNombre());
		
			clienteEntity.setApellido(individual.getApellido());
		
			clienteEntity.setFechaNacimiento(individual.getFechaNacimiento());
		
	}
	
	@Override
	public void modificarDatosClienteEmpresa(String idAdmin, String identificacion,  Empresa empresa) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Empresa clienteEntity = em.find(Empresa.class,empresa);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		if(identificacion != null) {
			clienteEntity.setIdentificacion(identificacion);
		}
		if(tipo != null) {
			clienteEntity.setTipoCliente(tipo);
		}
		if(estado != null) {
			clienteEntity.setEstado(estado);
		}
		if(fecha_alta != null) {
			clienteEntity.setFechaAlta(fecha_alta);
		}
		if(fecha_baja != null) {
			clienteEntity.setFechaBaja(fecha_baja);
		}
		
		if(direccion != null) {
			clienteEntity.setDireccion(direccion);
		}
		
		if(ciudad != null) {
			clienteEntity.setCiudad(ciudad);
		}
		
		if(codigo_postal != null) {
			clienteEntity.setCodigopostal(codigo_postal);
		}
		
		if(pais != null) {
			clienteEntity.setPais(pais);
		}
		
		if(razon_social != null) {
			clienteEntity.setRazonSocial(razon_social);
		}
		
	}

	@Override
	public List<Cliente> devolverTodosClientes(){
		TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> clientes= query.getResultList();
		return clientes;
	}
	
}
