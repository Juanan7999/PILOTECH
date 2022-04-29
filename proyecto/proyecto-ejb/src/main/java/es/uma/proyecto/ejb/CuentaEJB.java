package es.uma.proyecto.ejb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledAccountConSolo1CuentaExternaException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SegregadaNoExistenteException;
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
		
		pa.setDepositaEns(lcr);;	
		
		em.merge(pa);
		
		return pa;
	}

	@Override
	public void anadirAutorizados(Usuario usuario, List<PersonaAutorizada> lpa, CuentaFintech cf)
			throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		if (!cf.getCliente().getEstado().equals("J")) {
			throw new ClienteNoJuridicoException();
		}

		Empresa e = em.find(Empresa.class, cf.getCliente().getIdentificacion());

		List<Autorizacion> autorizaciones = e.getAutorizacions();

		for (PersonaAutorizada pa : lpa) {

			Autorizacion aut = new Autorizacion();
			aut.setTipo(null); // Revisar

			AutorizacionPK aut_pk = new AutorizacionPK();
			aut_pk.setEmpresaId(e.getIdentificacion());
			aut_pk.setPersonaAutorizadaId(pa.getIdentificacion());

			aut.setEmpresa(e);
			aut.setId(aut_pk);
			aut.setPersonaAutorizada(pa);
			em.persist(aut);
			autorizaciones.add(aut);

		}

		em.merge(e);
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
	public void eliminarAutorizados(Usuario usuario,  PersonaAutorizada pa)
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
	
	/*@Override
	public Cuenta devolver(String iban) throws CuentaNoExistenteException {
		Cuenta cuentaEntity = em.find(Cuenta.class, iban);
		if(cuentaEntity == null) {
			throw new CuentaNoExistenteException();
		}
		return cuentaEntity;
	}
	*/
	@Override 
	public List<Segregada> devolverInformeHolandaProductoTodas(String IBAN) throws CuentaNoExistenteException {
		List<Segregada> listadoCuentas = new ArrayList<>();
		Segregada seg = em.find(Segregada.class, IBAN);
		if(seg == null) {
			throw new CuentaNoExistenteException();
		}
		listadoCuentas.add(seg);
		return listadoCuentas;
	}
	
	@Override 
	public List<Segregada> devolverInformeHolandaProductoInactivas(String IBAN) throws CuentaNoExistenteException {
		
		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado");
		query.setParameter(" estado " , "baja");

		
		List<Segregada> listadoCuentas = query.getResultList();
		
		if(listadoCuentas == null) {
			throw new CuentaNoExistenteException();
		}
		
		return listadoCuentas;
	
	}
	
	@Override 
	public List<Segregada> devolverInformeHolandaProductoActivas(String IBAN) throws CuentaNoExistenteException {
		
		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado");
		query.setParameter(" estado " , "activa");
		
		List<Segregada> listadoCuentas = query.getResultList();
		
		if(listadoCuentas == null) {
			throw new CuentaNoExistenteException();
		}
		/*
		long mili = System.currentTimeMillis();
		Date dateNow = new Date(mili);*/
		
		
		return listadoCuentas;
		
	}
	
	@Override
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta, Date fechaBaja, String pais, String direccion) throws ClienteNoExistenteException{
		Query query = em.createQuery("SELECT i FROM Individual i where s.nombre = :nombre AND s.apellido = :apellido AND s.fechaAlta = :fechaalta"
				+ " AND s.fechaBaja = :fechabaja AND s.pais = :pais AND s.direccion = :direccion");
		
		query.setParameter("nombre" , nombre);
		query.setParameter("apellido" , apellidos);
		query.setParameter("fechaalta" , fechaAlta);
		query.setParameter("fechabaja" , fechaBaja);
		query.setParameter("pais", pais);
		query.setParameter("direccion" , direccion);
		
		
		
		List<Individual> listaClientes = query.getResultList();
		if(listaClientes == null) {
			throw new ClienteNoExistenteException();
		}
		
		return listaClientes;
	}
	
	/*@Override
	public List<PersonaAutorizada> devolverInformeHolandaAutorizados(String nombre, String apellidos, Date fechaAlta, Date fechaBaja, String pais, String direccion) throws ClienteNoExistenteException{
		Query query = em.createQuery("SELECT i FROM Individual i where s.nombre = :nombre AND s.apellido = :apellido AND s.fechaAlta = :fechaalta"
				+ " AND s.fechaBaja = :fechabaja AND s.direccion = :direccion");
		
		query.setParameter("nombre" , nombre);
		query.setParameter("apellido" , apellidos);
		query.setParameter("fechaalta" , fechaAlta);
		query.setParameter("fechabaja" , fechaBaja);
		query.setParameter("direccion" , direccion);
		
		
		List<Individual> listaClientes = query.getResultList();
		if(listaClientes == null) {
			throw new ClienteNoExistenteException();
		}
		
		return listaClientes;
	}*/
	
}
