package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Cliente;

import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteDesbloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
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

@Local
public interface GestionCuenta {

	/*
	 * Este metodo se encarga de comprobar en primera instancia si existe el
	 * administrador que esta intentado abrir la Cuenta, seguido de esto. En caso de
	 * que el administrador que intenta arbrir la cuenta no exista en la bd, salta
	 * una excepcion. En otro caso genera la cuenta segregada sin problemas. Junto
	 * con la informacion de la cuenta que tiene referenciada.
	 */

	public Segregada abrirCuentaFintechSegregada(Usuario usuario, Segregada cf, Cliente c, CuentaReferencia cr)
			throws UsuarioNoEsAdministrativoException;

	/*
	 * Este metodo se encarga de comprobar en primera instancia si existe el
	 * administrador que esta intentado abrir la Cuenta, seguido de esto. En caso de
	 * que el administrador que intenta arbrir la cuenta no exista en la bd, salta
	 * una excepcion. En otro caso genera la cuenta pooled sin problemas. Junto con
	 * la informacion de las cuentas que tiene referenciadas.
	 */

	public PooledAccount abrirCuentaFintechPooled(Usuario usuario, PooledAccount pa, Cliente c, List<DepositaEn> cr)
			throws UsuarioNoEsAdministrativoException, PooledAccountConSolo1CuentaExternaException;

	/*
	 * Este metodo se encarga de comprobar en primer lugar que la persona que esta
	 * tratando de a??adir a una Cuenta cuyo Cliente es una persona juridica sea
	 * administrativo. En caso de que no lo sea, salta una excepcion. Lo siguiente a
	 * comprobar es que el Cliente que trata de a??adir las personas autorizadas sea
	 * un cliente juridico. Si no se trata de un cliente juridico salta una
	 * excepcion. En caso de que no haya saltado ninguna excepcion se asocia
	 * correctamente todas estas personas autorizadas a la cuenta Empresa de el
	 * cliente en cuestion.
	 */

	public void anadirAutorizados(Usuario usuario, PersonaAutorizada pa, String identificacion)
			throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException, ClienteNoExistenteException;

	/*
	 * Este metodo se encarga de comprobar en primer lugar que la persona que esta
	 * tratando de modificar una Cuenta cuyo Cliente es una persona juridica sea
	 * administrativo. En caso de que no lo sea, salta una excepcion. Luego
	 * comprobamos que la persona autorizada de la cual vamos a modificar los datos
	 * exista. En caso de que no exista saltar??a una excepcion. En caso de que no
	 * haya saltado ninguna excepcion se modifican los datos de la persona
	 * autorizada en cuestion.
	 */
	
	

	public void modificarAutorizados(Usuario usuario, PersonaAutorizada pa) throws UsuarioNoEsAdministrativoException,
			 PersonaAutorizadaNoExistenteException;

	/*
	 * Para eliminar a una persona autorizada asociada a una un cliente jur??dico o
	 * Empresa, debemos de cambiar su estado y darlo de baja, para poder hacer una
	 * correcta auditor??a. Antes de lo anterior debemos comprobar que esta persona
	 * autorizada exista y sobre todo que el usuario que elimine al autorizado se
	 * trate de personal administrativo. En caso de que no se cumpla lo anterior,
	 * saltar??a una excepcion.
	 */

	public void eliminarAutorizados(Usuario usuario, PersonaAutorizada pa) throws UsuarioNoEsAdministrativoException,
			 PersonaAutorizadaNoExistenteException;

	/*
	 * Para que el personal administrativo pueda cerrar una cuenta segregada
	 * comprobamos si se trata de un usuario administrativo el que esta intentando
	 * cerrarla, en caso contrario saltaria una excepcion. Luego comprobamos que la
	 * cuenta segregada que se esta tratando de eliminar exista, en caso de que no
	 * exista, saltar??a una excepcion. Una vez comprobado lo anterior debemos
	 * comprobar que la cuenta externa asociada tenga saldo 0, si no lo tiene,
	 * saltaria una excepcion. Si ha pasado todas las comprobaciones anteriores,
	 * cambiamos el estado de la cuenta a "baja".
	 */

	public void cerrarCuentaSegregada(Usuario usuario, Segregada s)
			throws UsuarioNoEsAdministrativoException, SegregadaNoExistenteException, CuentaSinSaldo0Exception;

	/*
	 * Para que el personal administrativo pueda cerrar una cuenta pooled
	 * comprobamos si se trata de un usuario administrativo el que esta intentando
	 * cerrarla, en caso contrario saltaria una excepcion. Luego comprobamos que la
	 * cuenta pooled que se esta tratando de eliminar exista, en caso de que no
	 * exista, saltar??a una excepcion. Una vez comprobado lo anterior debemos
	 * comprobar que las cuentas externas asociadas tengan saldo 0, si no lo tiene,
	 * saltaria una excepcion. Si ha pasado todas las comprobaciones anteriores,
	 * cambiamos el estado de la cuenta a "baja".
	 */

	public void cerrarCuentaPooled(Usuario usuario, PooledAccount pa)
			throws UsuarioNoEsAdministrativoException, PooledNoExistenteException, CuentaSinSaldo0Exception;
	
	public Segregada devolverSegregada(String iban) throws SegregadaNoExistenteException;
	
	public PooledAccount devolverPooled(String iban) throws PooledNoExistenteException;
	
	public CuentaReferencia devolverCuentaReferencia(String iban) throws CuentaReferenciaNoExistenteException;

	void bloqueaAutorizado(Usuario admin, String id) throws PersonaAutorizadaNoExistenteException,
			ClienteBloqueadoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException;
	
	public void altaPersonaAutorizada(Usuario admin, PersonaAutorizada personaAutorizada) 
			throws PersonaAutorizadaExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException;
	public List<CuentaFintech> devolverCuentasDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException;
	public List<CuentaFintech> devolverCuentasDeIndividual(String id) throws ClienteNoExistenteException;
	public List<Segregada> devolverSegregadasDeIndividual(String id) throws ClienteNoExistenteException;
	public List<Segregada> devolverSegregadasDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException;
	public List<PooledAccount> devolverTodasPooled();
	public List<Segregada> devolverTodasSegregadas();
	public List<PooledAccount> devolverPooledDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException;
	
	public List<PooledAccount> devolverPooledDeIndividual(String id) throws ClienteNoExistenteException;
	public List<Transaccion> getTransaccionesSonOrigen(String iban) throws CuentaNoExistenteException;
	public List<Transaccion> getTransaccionesSonDestino(String iban) throws CuentaNoExistenteException;

	CuentaFintech devolverCuenta(String iban) throws CuentaNoExistenteException;
	public List<DepositaEn> getDepositadaEnDePooled(String iban) throws CuentaNoExistenteException;
	public List<Empresa> getEmpresasDeAutorizado(String id) throws PersonaAutorizadaNoExistenteException;
	List<PersonaAutorizada> devolverTodosAutorizados();

	void desbloqueaAutorizado(Usuario admin, String id) throws PersonaAutorizadaNoExistenteException,
			ClienteDesbloqueadoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException, ClienteYaDeBajaException;
	public PersonaAutorizada devolverPersonaAutorizada(String id) throws ClienteNoExistenteException;
	
	public List<CuentaReferencia> getCuentaReferenciasPooled(String iban) throws CuentaNoExistenteException;
	
	public List<String> getDivisas(String iban) throws CuentaNoExistenteException;
	public CuentaReferencia devolverCuentaReferencia_Divisa(String iban, String divisa) throws CuentaReferenciaNoExistenteException;
	
}
