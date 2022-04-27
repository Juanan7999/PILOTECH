package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaNoPooledException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.DivisaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioEsAdministrativoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Local
public interface GestionDivisa {

	//La aplicacion permitira a un cliente/autorizado realizar un cambio de divisas en una cuenta agrupada (pooled)
	//El origen y el destino es la misma cuenta. La cuenta tiene que tener saldo en las divisas de origen y destino
	//Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. 
	//Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también
	//No sera posible realizar un cambio de divisa en cuentas segregadas
	
	public void cambioDeDivisaCliente(String idAdmin,Transaccion idUnico) throws UsuarioEsAdministrativoException, ClienteNoExistenteException, ClienteYaDeBajaException, ClienteBloqueadoException, CuentasDiferentesException, DivisaNoExistenteException, CuentaNoPooledException;
	
	
	//La aplicacion permitira a un cliente/autorizado realizar un cambio de divisas en una cuenta agrupada (pooled)
	//El origen y el destino es la misma cuenta. La cuenta tiene que tener saldo en las divisas de origen y destino
	//Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. 
	//Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también
	//No sera posible realizar un cambio de divisa en cuentas segregadas
	
	public void cambioDeDivisaAdmin(String idAdmin,Transaccion idUnico) throws UsuarioNoEsAdministrativoException, CuentasDiferentesException, DivisaNoExistenteException, CuentaNoPooledException;
	
	
}
