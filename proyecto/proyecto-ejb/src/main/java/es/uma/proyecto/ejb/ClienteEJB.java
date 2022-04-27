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
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Stateless
public class ClienteEJB implements GestionCliente{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void altaClienteIndividual(Usuario admin, Cliente individual) throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {
		
		Individual clienteIndividualEntity = em.find(Individual.class, individual.getIdentificacion());
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteIndividualEntity != null) {
			throw new ClienteExistenteException();
		}
		
		em.persist(individual);
		 
	}

	@Override
	public void altaClienteEmpresa(Usuario admin, Cliente empresa) throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException{
		
		Empresa clienteEmpresaEntity = em.find(Empresa.class, empresa.getIdentificacion());
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteEmpresaEntity != null) {
			throw new ClienteExistenteException();
		}
				
		em.persist(empresa);
	}

	@Override
	public void bajaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
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
	public void activaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
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
	public void bloqueaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
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
	public void modificarDatosClienteIndividual(Usuario admin, String identificacionCliente, Individual individual) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException {
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Individual clienteEntity = em.find(Individual.class,identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		
		em.merge(individual);
		
	}
	
	@Override
	public void modificarDatosClienteEmpresa(Usuario admin, String identificacionEmpresa,  Empresa empresa) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException {
		
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());
		
		if(administrador==null) { //Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}
		
		if(!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Empresa clienteEntity = em.find(Empresa.class,identificacionEmpresa);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
			em.merge(empresa);
		
	}

	@Override
	public List<Cliente> devolverTodosClientes(){
		TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> clientes= query.getResultList();
		return clientes;
	}
	
	@Override
	public Cliente devolver(String identificacion) throws ClienteNoExistenteException {
		Cliente clienteEntity = em.find(Cliente.class, identificacion);
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		return clienteEntity;
	}
	
}
