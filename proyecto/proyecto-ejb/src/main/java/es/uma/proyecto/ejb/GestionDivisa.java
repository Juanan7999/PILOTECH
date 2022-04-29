package es.uma.proyecto.ejb;



import javax.ejb.Local;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Local
public interface GestionDivisa {

	//La aplicacion permitira a un cliente/autorizado realizar un cambio de divisas en una cuenta agrupada (pooled)
	//El origen y el destino es la misma cuenta. La cuenta tiene que tener saldo en las divisas de origen y destino
	//Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. 
	//Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también
	//No sera posible realizar un cambio de divisa en cuentas segregadas
	
	public void cambioDeDivisaCliente_Autorizado(String id,PooledAccount pa, CuentaReferencia origen, CuentaReferencia destino,Double cantidad, Transaccion t) throws 
	CuentasDiferentesException, ClientePersonaAutorizadaNoEncontradoException, PooledNoExistenteException, SaldoInsuficienteException;
	
	
	//La aplicacion permitira a un cliente/autorizado realizar un cambio de divisas en una cuenta agrupada (pooled)
	//El origen y el destino es la misma cuenta. La cuenta tiene que tener saldo en las divisas de origen y destino
	//Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. 
	//Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también
	//No sera posible realizar un cambio de divisa en cuentas segregadas
	
	public void cambioDeDivisaAdmin(Usuario userAdmin, String id, PooledAccount cuentaP, CuentaReferencia origen,
			CuentaReferencia destino, Double cantidadOrigen, Transaccion t) throws 
			CuentasDiferentesException, ClientePersonaAutorizadaNoEncontradoException, PooledNoExistenteException, SaldoInsuficienteException, UsuarioNoEsAdministrativoException;
	
	
}
