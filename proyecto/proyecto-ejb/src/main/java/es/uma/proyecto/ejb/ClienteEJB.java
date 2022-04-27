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
		Individual clienteIndividualEntity = em.find(Individual.class, individual.getIdentificacion());
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
		Empresa clienteEmpresaEntity = em.find(Empresa.class, empresa.getIdentificacion());
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
	public void bajaCliente(String idAdmin, String idCliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		Cliente clienteEntity = em.find(Cliente.class, idCliente);
		
		
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
	public void activaCliente(String idAdmin, String idCliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Cliente clienteEntity = em.find(Cliente.class, idCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteYaActivoException();
		}
		
		clienteEntity.setEstado("activo");
		
	}

	@Override
	public void bloqueaCliente(String idAdmin, String idCliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		
		Cliente clienteEntity = em.find(Cliente.class,idCliente);
		
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
	
			clienteEntity.setFechaAlta(Date.valueOf(individual.getFechaAlta().toString()));
		
			clienteEntity.setFechaBaja(Date.valueOf(individual.getFechaBaja().toString()));
		
			clienteEntity.setDireccion(individual.getDireccion());
		
			clienteEntity.setCiudad(individual.getCiudad());
	
			clienteEntity.setCodigopostal(individual.getCodigopostal());
		
			clienteEntity.setPais(individual.getPais());
	
			clienteEntity.setNombre(individual.getNombre());
		
			clienteEntity.setApellido(individual.getApellido());
		
			clienteEntity.setFechaNacimiento(Date.valueOf(individual.getFechaNacimiento().toString()));
		
	}
	
	@Override
	public void modificarDatosClienteEmpresa(String idAdmin, String identificacionEmpresa,  Empresa empresa) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Empresa clienteEntity = em.find(Empresa.class,identificacionEmpresa);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
			clienteEntity.setIdentificacion(empresa.getIdentificacion());
		
			clienteEntity.setTipoCliente(empresa.getTipoCliente());
		
			clienteEntity.setEstado(empresa.getEstado());
	
			clienteEntity.setFechaAlta(Date.valueOf(empresa.getFechaAlta().toString()));
		
			clienteEntity.setFechaBaja(Date.valueOf(empresa.getFechaBaja().toString()));
		
			clienteEntity.setDireccion(empresa.getDireccion());
		
			clienteEntity.setCiudad(empresa.getCiudad());
		
			clienteEntity.setCodigopostal(empresa.getCodigopostal());
		
			clienteEntity.setPais(empresa.getPais());
		
			clienteEntity.setRazonSocial(empresa.getRazonSocial());
		
	}

	@Override
	public List<Cliente> devolverTodosClientes(){
		TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> clientes= query.getResultList();
		return clientes;
	}
	
}
