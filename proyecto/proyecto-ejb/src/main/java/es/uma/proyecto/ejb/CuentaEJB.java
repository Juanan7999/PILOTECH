package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.AutorizacionPK;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledAccountConSolo1CuentaExternaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class CuentaEJB implements GestionCuenta{

private static final Logger LOG = Logger.getLogger(CuentaEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void abrirCuentaFintechSegregada(String idAdm, String iban, String swift, String estado,
			Date fecha_apertura, Date fecha_cierre, String clasificacion, String comision, CuentaReferencia cr) throws UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdm);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Segregada cuentaSegregada = new Segregada();
		
		cuentaSegregada.setIban(iban);
		cuentaSegregada.setSwift(swift);
		cuentaSegregada.setEstado(estado);
		cuentaSegregada.setFechaApertura(fecha_apertura);
		
		if(fecha_cierre !=null) {
			cuentaSegregada.setFechaCierre(fecha_cierre);
		}
		
		if(clasificacion != null) {
			cuentaSegregada.setClasificacion(clasificacion);
		}
		
		if(comision != null) {
			cuentaSegregada.setComision(comision);
		}
		
		cuentaSegregada.setCuentaReferencia(cr);
		em.persist(cuentaSegregada);
	}

	@Override
	public void abrirCuentaFintechPooled(String idAdm, String iban, String swift, String estado, Date fecha_apertura,
			Date fecha_cierre, String clasificacion, List<DepositaEn> lcr) throws UsuarioNoEsAdministrativoException, PooledAccountConSolo1CuentaExternaException {
		
		Usuario administrador = em.find(Usuario.class, idAdm);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		PooledAccount cuentaPooled = new PooledAccount();
		
		cuentaPooled.setIban(iban);
		cuentaPooled.setSwift(swift);
		cuentaPooled.setEstado(estado);
		cuentaPooled.setFechaApertura(fecha_apertura);
		
		if(fecha_cierre !=null) {
			cuentaPooled.setFechaCierre(fecha_cierre);
		}
		
		if(clasificacion != null) {
			cuentaPooled.setClasificacion(clasificacion);
		}
		
		if(lcr.size()<2) {
			throw new PooledAccountConSolo1CuentaExternaException();
		}
		
		cuentaPooled.setDepositaEns(lcr);
		em.persist(cuentaPooled);
		
	}

	@Override
	public void anadirAutorizados(String idAdm, List<PersonaAutorizada> lpa, Cliente c) throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException {
		
		Usuario administrador = em.find(Usuario.class, idAdm);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(!c.getEstado().equals("J")) {
			throw new ClienteNoJuridicoException();
		}
		
		Empresa e = em.find(Empresa.class, c.getId());
		
		List<Autorizacion> autorizaciones = e.getAutorizacions();
		
		for(PersonaAutorizada pa : lpa) {
			
			Autorizacion aut = new Autorizacion();
			aut.setTipo(null); //Revisar
			
			AutorizacionPK aut_pk = new AutorizacionPK();
			aut_pk.setEmpresaId(e.getId());
			aut_pk.setPersonaAutorizadaId(pa.getId());
			
			aut.setEmpresa(e);
			aut.setId(aut_pk);
			aut.setPersonaAutorizada(pa);
			em.persist(aut);
			autorizaciones.add(aut);
			
		}
		
		e.setAutorizacions(autorizaciones);
		}

	@Override
	public void modificarAutorizados(String idAdm, Cliente c, String id, String identificacion, String nombre, String apellidos, String direccion, Date fecha_nacimiento, String Estado, Date FechaInicio, Date fechaFin) throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException, PersonaAutorizadaNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdm);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		PersonaAutorizada p = em.find(PersonaAutorizada.class, id);
		
		if(p==null) {
			throw new PersonaAutorizadaNoExistenteException();
		}
		
		if(!c.getEstado().equals("J")) {
			throw new ClienteNoJuridicoException();
		}
		
		p.setId(id);
		p.setIdentificacion(identificacion);
		p.setNombre(nombre);
		p.setApellidos(apellidos);
		p.setDireccion(direccion);
		
		if(fecha_nacimiento !=null) {
			p.setFechaNacimiento(fecha_nacimiento);
		}
		
		p.setFechaNacimiento(fecha_nacimiento);
		
		if(Estado != null) {
			p.setEstado(Estado);
		}
		
		if(FechaInicio != null) {
			p.setFechainicio(FechaInicio);
		}
		
		if(fechaFin != null) {
			p.setFechafin(fechaFin);
		}
		
	}
}
