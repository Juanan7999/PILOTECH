package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledAccountConSolo1CuentaExternaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Local
public interface GestionCuenta {

	/* Este metodo se encarga de comprobar en primera instancia si existe el administrador que esta intentado abrir la Cuenta,
	 * seguido de esto. En caso de que el administrador que intenta arbrir la cuenta no exista en la bd, salta una excepcion.
	 * En otro caso genera la cuenta segregada sin problemas. Junto con la informacion de la cuenta que tiene referenciada.
	 */
	
	public void abrirCuentaFintechSegregada(String idAdm, String iban, String swift, String estado, Date fecha_apertura, Date fecha_cierre, String clasificacion, String comision, CuentaReferencia cr) throws UsuarioNoEsAdministrativoException;
	
	/* Este metodo se encarga de comprobar en primera instancia si existe el administrador que esta intentado abrir la Cuenta,
	 * seguido de esto. En caso de que el administrador que intenta arbrir la cuenta no exista en la bd, salta una excepcion.
	 * En otro caso genera la cuenta pooled sin problemas. Junto con la informacion de las cuentas que tiene referenciadas.
	 */
	
	public void abrirCuentaFintechPooled(String idAdm, String iban, String swift, String estado, Date fecha_apertura, Date fecha_cierre, String clasificacion, List<DepositaEn> lcr) throws UsuarioNoEsAdministrativoException, PooledAccountConSolo1CuentaExternaException;
	
	/* Este metodo se encarga de comprobar en primer lugar que la persona que esta tratando de añadir a una Cuenta cuyo Cliente es una persona juridica 
	 * sea administrativo. En caso de que no lo sea, salta una excepcion. 
	 * Lo siguiente a comprobar es que el Cliente que trata de añadir las personas autorizadas sea un cliente juridico. Si no se trata de un cliente juridico salta una excepcion.
	 * En caso de que no haya saltado ninguna excepcion se asocia correctamente todas estas personas autorizadas a la cuenta Empresa de el cliente en cuestion.
	 */

	public void anadirAutorizados(String idAdm, List<PersonaAutorizada> lpa, Cliente c) throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException;
	
	/* Este metodo se encarga de comprobar en primer lugar que la persona que esta tratando de modificar una Cuenta cuyo Cliente es una persona juridica
	 * sea administrativo. En caso de que no lo sea, salta una excepcion.
	 * Lo siguiente a comprobar es que el Cliente al cual trata de modificarse  la persona autorizada sea un cliente juridico. Si no se trata de un cliente juridico salta una excepcion.
	 * En caso de que no haya saltado ninguna excepcion se modifican los datos de la persona autorizada en cuestion.
	 */
	
	public void modificarAutorizados(String idAdm, Cliente c, String id, String identificacion, String nombre, String apellidos, String direccion, Date fecha_nacimiento, String Estado, Date FechaInicio, Date fechaFin) throws UsuarioNoEsAdministrativoException, ClienteNoJuridicoException, PersonaAutorizadaNoExistenteException;
	
	
}
