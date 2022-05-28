package es.uma.proyecto.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.AutorizacionPK;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaReferenciaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledAccountConSolo1CuentaExternaException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SegregadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Stateless
public class CuentaEJB implements GestionCuenta {

	private static final Logger LOG = Logger.getLogger(CuentaEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public Segregada abrirCuentaFintechSegregada(Usuario usuario, Segregada cf, Cliente c, CuentaReferencia cr)
			throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		cf.setCuentaReferencia(cr);
		cf.setCliente(c);
		em.persist(cf);

		return cf;
	}

	@Override
	public PooledAccount abrirCuentaFintechPooled(Usuario usuario, PooledAccount pa, Cliente c, List<DepositaEn> lcr)
			throws UsuarioNoEsAdministrativoException, PooledAccountConSolo1CuentaExternaException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		pa.setCliente(c);

		pa.setDepositaEns(lcr);
		;

		em.merge(pa);

		return pa;
	}

	@Override
	public void anadirAutorizados(Usuario usuario, PersonaAutorizada pa, Cliente cl)
			throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		if (!cl.getEstado().equals("J")) {
			throw new ClienteNoJuridicoException();
		}

		Empresa e = em.find(Empresa.class, cl.getIdentificacion());

		List<Autorizacion> autorizaciones = e.getAutorizacions();

	

			Autorizacion aut = new Autorizacion();
			aut.setTipo(1); // Revisar

			AutorizacionPK aut_pk = new AutorizacionPK();
			aut_pk.setEmpresaId(e.getIdentificacion());
			aut_pk.setPersonaAutorizadaId(pa.getIdentificacion());

			aut.setEmpresa(e);
			aut.setId(aut_pk);
			aut.setPersonaAutorizada(pa);
			em.persist(aut);
			autorizaciones.add(aut);

		

		em.merge(e);
	}
	
	@Override
	public void altaPersonaAutorizada(Usuario admin, PersonaAutorizada personaAutorizada)
			throws PersonaAutorizadaExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {

		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, personaAutorizada.getIdentificacion());

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		if (personaAutorizadaEntity != null) {
			throw new PersonaAutorizadaExistenteException();
		}
		
		personaAutorizada.setEstado("activo");
		personaAutorizada.setFechaInicio(LocalDate.now().toString());
		em.persist(personaAutorizada);
		

	}

	@Override
	public void modificarAutorizados(Usuario usuario, PersonaAutorizada pa)
			throws UsuarioNoEsAdministrativoException, PersonaAutorizadaNoExistenteException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		PersonaAutorizada p = em.find(PersonaAutorizada.class, pa.getIdentificacion());

		if (p == null) {
			throw new PersonaAutorizadaNoExistenteException();
		}

		em.merge(pa);
	}

	@Override
	public void eliminarAutorizados(Usuario usuario, PersonaAutorizada pa)
			throws UsuarioNoEsAdministrativoException, PersonaAutorizadaNoExistenteException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		PersonaAutorizada p = em.find(PersonaAutorizada.class, pa.getIdentificacion());

		if (p == null) {

			throw new PersonaAutorizadaNoExistenteException();
		}

		p.setEstado("baja");

	}

	@Override
	public void cerrarCuentaSegregada(Usuario usuario, Segregada s)
			throws UsuarioNoEsAdministrativoException, SegregadaNoExistenteException, CuentaSinSaldo0Exception {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		Segregada segregada = em.find(Segregada.class, s.getIban());

		if (segregada == null) {
			throw new SegregadaNoExistenteException();
		}

		if (s.getCuentaReferencia().getSaldo() != 0) {
			throw new CuentaSinSaldo0Exception();
		}

		segregada.setEstado("baja");
		em.merge(segregada);
	}

	@Override
	public void cerrarCuentaPooled(Usuario usuario, PooledAccount pa)
			throws UsuarioNoEsAdministrativoException, PooledNoExistenteException, CuentaSinSaldo0Exception {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		PooledAccount pooled = em.find(PooledAccount.class, pa.getIban());

		if (pooled == null) {
			throw new PooledNoExistenteException();
		}

		for (DepositaEn de : pa.getDepositaEns()) {

			if (de.getSaldo() != 0) {

				throw new CuentaSinSaldo0Exception();
			}
		}

		pooled.setEstado("baja");
		em.merge(pooled);
	}
	
	public Segregada devolverSegregada(String iban) throws SegregadaNoExistenteException {
		TypedQuery<Segregada> query = em.createQuery("SELECT c FROM Segregada c where c.iban = :fiban", Segregada.class);
		query.setParameter("fiban", iban);
		Segregada segregada= query.getSingleResult();
		
		if(segregada == null) {
			
			throw new SegregadaNoExistenteException();
		}
		
		return segregada;
	}
	
	public PooledAccount devolverPooled(String iban) throws PooledNoExistenteException {
		TypedQuery<PooledAccount> query = em.createQuery("SELECT c FROM PooledAccount c where c.iban = :fiban", PooledAccount.class);
		query.setParameter("fiban", iban);
		PooledAccount pooled = query.getSingleResult();
		
		if(pooled == null) {
			
			throw new PooledNoExistenteException();
		}
		
		return pooled;
	}
	
	public CuentaReferencia devolverCuentaReferencia(String iban) throws CuentaReferenciaNoExistenteException {
		CuentaReferencia cuenta = em.find(CuentaReferencia.class, iban);
		
		if(cuenta == null) {
			
			throw new CuentaReferenciaNoExistenteException();
		}
		
		return cuenta;
	}
	
	@Override
	public void bloqueaAutorizado(Usuario admin, String id) throws PersonaAutorizadaNoExistenteException,
			ClienteBloqueadoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException {

		Usuario administrador = em.find(Usuario.class, admin.getNombreUsuario());

		if (administrador == null) { // Si no existe o no es administrativo
			throw new UsuarioNoEncontradoException();
		}

		if (!administrador.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}

		PersonaAutorizada pa = em.find(PersonaAutorizada.class, id);

		if (pa == null) {
			throw new PersonaAutorizadaNoExistenteException();
		} else if (pa.getEstado().endsWith("bloqueado")) {
			throw new ClienteBloqueadoException();
		}

		pa.setEstado("bloqueado");

	}
	
	public List<CuentaFintech> devolverCuentasDeIndividual(String id) throws ClienteNoExistenteException{
		Individual cliente = em.find(Individual.class, id);
		
			if(cliente == null) {
				throw new ClienteNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM CuentaFintech c");
		List<CuentaFintech> listaCuentas = query.getResultList();
		List<CuentaFintech> listaRes = new ArrayList<>();
		for(CuentaFintech c : listaCuentas) {
			if(c.getCliente().getIdentificacion().equals(id)) {
				listaRes.add(c);
			}
		}
		
		return listaRes;
	}
	
	public List<CuentaFintech> devolverCuentasDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException{
		PersonaAutorizada cliente = em.find(PersonaAutorizada.class, id);
		
			if(cliente == null) {
				throw new PersonaAutorizadaNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM CuentaFintech c");
		Query query2 = em.createQuery("SELECT c FROM Autorizacion c where c.personaAutorizada.identificacion = :id");
		query2.setParameter("id", id);
		List<Autorizacion> autorizaciones = query2.getResultList();
		List<CuentaFintech> listaCuentas = query.getResultList();
		List<CuentaFintech> listaRes = new ArrayList<>();
		
		for(CuentaFintech c : listaCuentas) {
			
			
			Cliente cl = c.getCliente();
			
			if(cl.getTipoCliente().equals("J")) {
				Empresa empresa = em.find(Empresa.class,cl.getIdentificacion());
				
				boolean esta = false;
				for(Autorizacion aut : autorizaciones) {
					if(aut.getEmpresa().getIdentificacion().equals(empresa.getIdentificacion())) {
						esta = true;
					}
					
				}
				
				if(esta) {
					listaRes.add(c);
				}
			}
			
			
		}
		
		return listaRes;
	}
	
	public List<Segregada> devolverSegregadasDeIndividual(String id) throws ClienteNoExistenteException{
		Individual cliente = em.find(Individual.class, id);
		
			if(cliente == null) {
				throw new ClienteNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM Segregada c");
		List<Segregada> listaCuentas = query.getResultList();
		List<Segregada> listaRes = new ArrayList<>();
		for(Segregada c : listaCuentas) {
			if(c.getCliente().getIdentificacion().equals(id)) {
				listaRes.add(c);
			}
		}
		
		return listaRes;
	}
	
	
	public List<Segregada> devolverSegregadasDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException{
		PersonaAutorizada cliente = em.find(PersonaAutorizada.class, id);
		
			if(cliente == null) {
				throw new PersonaAutorizadaNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM Segregada c");
		Query query2 = em.createQuery("SELECT c FROM Autorizacion c where c.personaAutorizada.identificacion = :id");
		query2.setParameter("id", id);
		List<Autorizacion> autorizaciones = query2.getResultList();
		List<Segregada> listaCuentas = query.getResultList();
		List<Segregada> listaRes = new ArrayList<>();
		
		for(Segregada c : listaCuentas) {
			
			
			Cliente cl = c.getCliente();
			
			if(cl.getTipoCliente().equals("J")) {
				Empresa empresa = em.find(Empresa.class,cl.getIdentificacion());
				
				boolean esta = false;
				for(Autorizacion aut : autorizaciones) {
					if(aut.getEmpresa().getIdentificacion().equals(empresa.getIdentificacion())) {
						esta = true;
					}
					
				}
				
				if(esta) {
					listaRes.add(c);
				}
			}
			
			
		}
		
		return listaRes;
	}
	//Devuelve las transacciones de una cuenta en las que la cuenta es la origen 
	public List<Transaccion> getTransaccionesSonOrigen(String iban) throws CuentaNoExistenteException{
		CuentaFintech cuenta = em.find(CuentaFintech.class, iban);
		
		if(cuenta == null) {
			throw new CuentaNoExistenteException();
		}
		Query query = em.createQuery("SELECT c FROM Transaccion c where c.cuenta1.iban = :iban");
		query.setParameter("iban", cuenta.getIban());
		List<Transaccion> transacciones = query.getResultList();
		return transacciones;
		
	}
	
	//Devuelve las transacciones de una cuenta en las que la cuenta es la destino 
		public List<Transaccion> getTransaccionesSonDestino(String iban) throws CuentaNoExistenteException{
			CuentaFintech cuenta = em.find(CuentaFintech.class, iban);
			
			if(cuenta == null) {
				throw new CuentaNoExistenteException();
			}
			Query query = em.createQuery("SELECT c FROM Transaccion c where c.cuenta2.iban = :iban");
			query.setParameter("iban", cuenta.getIban());
			List<Transaccion> transacciones = query.getResultList();
			return transacciones;
			
		}
	
	public List<Segregada> devolverTodasSegregadas(){
		TypedQuery<Segregada> query = em.createQuery("SELECT c FROM Segregada c", Segregada.class);
		List<Segregada> segregadas = query.getResultList();
		return segregadas;
		
		
	}
	
	
	public List<PooledAccount> devolverTodasPooled(){
		TypedQuery<PooledAccount> query = em.createQuery("SELECT c FROM PooledAccount c", PooledAccount.class);
		List<PooledAccount> cuentasPooled = query.getResultList();
		return cuentasPooled;
		
		
	}
	
	public List<PooledAccount> devolverPooledDeIndividual(String id) throws ClienteNoExistenteException{
		Individual cliente = em.find(Individual.class, id);
		
			if(cliente == null) {
				throw new ClienteNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM PooledAccount c");
		List<PooledAccount> listaCuentas = query.getResultList();
		List<PooledAccount> listaRes = new ArrayList<>();
		for(PooledAccount c : listaCuentas) {
			if(c.getCliente().getIdentificacion().equals(id)) {
				listaRes.add(c);
			}
		}
		
		return listaRes;
	}
	
	
	public List<PooledAccount> devolverPooledDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException{
		PersonaAutorizada cliente = em.find(PersonaAutorizada.class, id);
		
			if(cliente == null) {
				throw new PersonaAutorizadaNoExistenteException();
			}
			
			
		Query query = em.createQuery("SELECT c FROM PooledAccount c");
		Query query2 = em.createQuery("SELECT c FROM Autorizacion c where c.personaAutorizada.identificacion = :id");
		query2.setParameter("id", id);
		List<Autorizacion> autorizaciones = query2.getResultList();
		List<PooledAccount> listaCuentas = query.getResultList();
		List<PooledAccount> listaRes = new ArrayList<>();
		
		for(PooledAccount c : listaCuentas) {
			
			
			Cliente cl = c.getCliente();
			
			if(cl.getTipoCliente().equals("J")) {
				Empresa empresa = em.find(Empresa.class,cl.getIdentificacion());
				
				boolean esta = false;
				for(Autorizacion aut : autorizaciones) {
					if(aut.getEmpresa().getIdentificacion().equals(empresa.getIdentificacion())) {
						esta = true;
					}
					
				}
				
				if(esta) {
					listaRes.add(c);
				}
			}
			
			
		}
		
		return listaRes;
	}
	
	@Override
	public CuentaFintech devolverCuenta(String iban) throws CuentaNoExistenteException{
		CuentaFintech cuenta = em.find(CuentaFintech.class, iban);
		
		if(cuenta == null) {
			throw new CuentaNoExistenteException();
		}
		
		return cuenta;
	}
	

	
	
	public List<DepositaEn> getDepositadaEnDePooled(String iban) throws CuentaNoExistenteException{
		PooledAccount pooled = em.find(PooledAccount.class, iban);
		
		if(pooled == null) {
			throw new CuentaNoExistenteException();
		}
		
		Query query = em.createQuery("SELECT c FROM DepositaEn c where c.pooledAccount.iban = :iban");
		query.setParameter("iban", iban);
		List<DepositaEn> depositados = query.getResultList();
		
		
		return depositados;
		
	}
	
	@Override
	public List<PersonaAutorizada> devolverTodosAutorizados(){
		Query query = em.createQuery("SELECT p FROM PersonaAutorizada p");
		List<PersonaAutorizada> autorizados = query.getResultList();
		return autorizados;
	}
	
}
