package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.AutorizacionPK;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
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
	public void abrirCuentaFintechSegregada(String idAdm, Segregada cf, CuentaReferencia cr)
			throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, idAdm);

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		cf.setCuentaReferencia(cr);
		em.persist(cf);
	}

	@Override
	public void abrirCuentaFintechPooled(String idAdm, PooledAccount pa, List<DepositaEn> ld)
			throws UsuarioNoEsAdministrativoException, PooledAccountConSolo1CuentaExternaException {

		Usuario administrador = em.find(Usuario.class, idAdm);

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		if (ld.size() < 2) {
			throw new PooledAccountConSolo1CuentaExternaException();
		}

		pa.setDepositaEns(ld);
		em.persist(pa);

	}

	@Override
	public void anadirAutorizados(String idAdm, List<PersonaAutorizada> lpa, Cliente c)
			throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException {

		Usuario administrador = em.find(Usuario.class, idAdm);

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		if (!c.getEstado().equals("J")) {
			throw new ClienteNoJuridicoException();
		}

		Empresa e = em.find(Empresa.class, c.getIdentificacion());

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

		e.setAutorizacions(autorizaciones);
	}

	@Override
	public void modificarAutorizados(String idAdm, PersonaAutorizada pa)
			throws UsuarioNoEsAdministrativoException, PersonaAutorizadaNoExistenteException {

		Usuario administrador = em.find(Usuario.class, idAdm);

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		PersonaAutorizada p = em.find(PersonaAutorizada.class, pa.getIdentificacion());

		if (p == null) {
			throw new PersonaAutorizadaNoExistenteException();
		}

		p.setIdentificacion(pa.getIdentificacion());
		p.setNombre(pa.getNombre());
		p.setApellidos(pa.getApellidos());
		p.setDireccion(pa.getDireccion());
		p.setFechaNacimiento(pa.getFechaNacimiento());
		p.setEstado(pa.getEstado());
		p.setFechainicio(pa.getFechainicio());
	}

	@Override
	public void eliminarAutorizados(String idAdm, PersonaAutorizada pa)
			throws UsuarioNoEsAdministrativoException, PersonaAutorizadaNoExistenteException {

		Usuario administrador = em.find(Usuario.class, idAdm);

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
	public void cerrarCuentaSegregada(String idAdm, Segregada s)
			throws UsuarioNoEsAdministrativoException, SegregadaNoExistenteException, CuentaSinSaldo0Exception {

		Usuario administrador = em.find(Usuario.class, idAdm);

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
	}

	@Override
	public void cerrarCuentaPooled(String idAdm, PooledAccount pa)
			throws UsuarioNoEsAdministrativoException, PooledNoExistenteException, CuentaSinSaldo0Exception {

		Usuario administrador = em.find(Usuario.class, idAdm);

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		PooledAccount pooled = em.find(PooledAccount.class, pa.getIban());

		if (pooled == null) {
			throw new PooledNoExistenteException();
		}

		for (DepositaEn de : pa.getDepositaEns()) {

			if (de.getCuentaReferencia().getSaldo() != 0) {

				throw new CuentaSinSaldo0Exception();
			}
		}
		
		pooled.setEstado("baja");
	}

}
