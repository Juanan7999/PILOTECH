package es.uma.proyecto.ejb;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Stateless
public class ClienteEJB implements GestionCliente {

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void altaClienteIndividual(Usuario admin, Individual individual)
			throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {

		Individual clienteIndividualEntity = em.find(Individual.class, individual.getIdentificacion());

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		if (clienteIndividualEntity != null) {
			throw new ClienteExistenteException();
		}
		
		individual.setFechaAlta(LocalDate.now().toString());
		individual.setEstado("activo");
		individual.setTipoCliente("F");
		em.persist(individual);

	}
	
	

	@Override
	public void altaClienteEmpresa(Usuario admin, Empresa empresa)
			throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {

		Empresa clienteEmpresaEntity = em.find(Empresa.class, empresa.getIdentificacion());

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		if (clienteEmpresaEntity != null) {
			throw new ClienteExistenteException();
		}
		
		empresa.setFechaAlta(LocalDate.now().toString());
		empresa.setEstado("activo");
		empresa.setTipoCliente("J");
		em.persist(empresa);
	}

	@Override
	public void bajaCliente(Usuario admin, String idCliente)
			throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException,
			UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {
		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		Cliente clienteEntity = em.find(Cliente.class, idCliente);

		if (clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().equals("baja")) {
			throw new ClienteYaDeBajaException();
		}

		List<CuentaFintech> cuentas = clienteEntity.getCuentaFinteches();

		for (CuentaFintech c : cuentas) {
			if (c.getEstado().equals("activa")) {
				throw new CuentaAbiertaException();
			}
		}

		clienteEntity.setEstado("baja");
		clienteEntity.setFechaBaja(LocalDate.now().toString());
	}

	@Override
	public void activaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException,
			ClienteYaActivoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException, ClienteYaDeBajaException {

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		Cliente clienteEntity = em.find(Cliente.class, idCliente);

		if (clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteYaActivoException();
		} else if (clienteEntity.getEstado().endsWith("baja")) {
			throw new ClienteYaDeBajaException();
		}

		clienteEntity.setEstado("activo");
	}

	@Override
	public void bloqueaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException,
			ClienteBloqueadoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException, ClienteYaDeBajaException {

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		Cliente clienteEntity = em.find(Cliente.class, idCliente);

		if (clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("bloqueado")) {
			throw new ClienteBloqueadoException();
		} else if (clienteEntity.getEstado().endsWith("bloqueado")) {
			throw new ClienteYaDeBajaException();
		}

		clienteEntity.setEstado("bloqueado");

	}

	@Override
	public void modificarDatosClienteIndividual(Usuario admin, String identificacionCliente, Individual individual)
			throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException {

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		Individual clienteEntity = em.find(Individual.class, identificacionCliente);

		if (clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		individual.setEstado(clienteEntity.getEstado());
		individual.setFechaAlta(clienteEntity.getFechaAlta().toString());
		individual.setTipoCliente(clienteEntity.getTipoCliente());
		if(clienteEntity.getFechaBaja() != null) {
			individual.setFechaBaja(clienteEntity.getFechaBaja().toString());
		}
		
		em.merge(individual);

	}

	@Override
	public void modificarDatosClienteEmpresa(Usuario admin, String identificacionEmpresa, Empresa empresa)
			throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException {

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		Empresa clienteEntity = em.find(Empresa.class, identificacionEmpresa);

		if (clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		empresa.setEstado(clienteEntity.getEstado());
		empresa.setFechaAlta(clienteEntity.getFechaAlta().toString());
		empresa.setTipoCliente(clienteEntity.getTipoCliente());
		if(clienteEntity.getFechaBaja() != null) {
			empresa.setFechaBaja(clienteEntity.getFechaBaja().toString());
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
	public Cliente devolverCliente(String identificacion) throws ClienteNoExistenteException{
		Individual cliente = em.find(Individual.class, identificacion);
		
		if(cliente == null) {
			throw new ClienteNoExistenteException();
		}
		
		return cliente;
	}
		
	@Override
	public Empresa devolverClienteEmpresa(String identificacion) throws ClienteNoExistenteException{
		Empresa cliente = em.find(Empresa.class, identificacion);
		
		if(cliente == null) {
			throw new ClienteNoExistenteException();
		}
		
		return cliente;
	}
	
	@Override
	public PersonaAutorizada devolverPersonaAut(String identificacion) throws PersonaAutorizadaNoExistenteException{
		TypedQuery<PersonaAutorizada> query = em.createQuery("SELECT p FROM PersonaAutorizada p where p.identificacion = :fiden", PersonaAutorizada.class);
		query.setParameter("fiden", identificacion);
		PersonaAutorizada persona= query.getSingleResult();
		
		if(persona == null) {
			throw new PersonaAutorizadaNoExistenteException();
		}
		
		return persona;
	}
	
	@Override
	public List<Individual> devolverTodosIndividuales(){
		TypedQuery<Individual> query = em.createQuery("SELECT c FROM Individual c", Individual.class);
		List<Individual> clientes= query.getResultList();
		return clientes;
	}
	
	@Override
	public List<Empresa> devolverTodosEmpresa(){
		TypedQuery<Empresa> query = em.createQuery("SELECT c FROM Empresa c", Empresa.class);
		List<Empresa> clientes= query.getResultList();
		return clientes;
	}
}
