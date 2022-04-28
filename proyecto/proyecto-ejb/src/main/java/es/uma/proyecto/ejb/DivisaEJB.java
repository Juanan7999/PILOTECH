package es.uma.proyecto.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaNoPooledException;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.DivisaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioEsAdministrativoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class DivisaEJB implements GestionDivisa{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	
	@Override
	public void cambioDeDivisaCliente(String idAdmin,Transaccion idUnico,Double cantidad) throws UsuarioEsAdministrativoException, ClienteNoExistenteException, ClienteBloqueadoException, ClienteYaDeBajaException, CuentasDiferentesException, DivisaNoExistenteException, CuentaNoPooledException, CuentaNoExistenteException, CuentaSinSaldo0Exception {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador!=null && administrador.getTipo().equals("A")) {
			throw new UsuarioEsAdministrativoException();
		}
		
			
		
		Transaccion transEntity = em.find(Transaccion.class, idUnico);
		
		 Divisa origen = transEntity.getDivisa1();
		 Divisa destino = transEntity.getDivisa2();
		 
		 if(origen == null) {
			 throw new DivisaNoExistenteException();
		 }
		 
		 if(destino == null) {
			 throw new DivisaNoExistenteException();
		 }
		 
		 Cuenta c1 = new Cuenta();
		 c1 = transEntity.getCuenta1();
		 
		 if(!(c1 instanceof PooledAccount) ) {
			 throw new CuentaNoPooledException();
		 }
		 
		 Cuenta c2 = new Cuenta();
		 c2 = transEntity.getCuenta2();
		 
		 if(!(c2 instanceof PooledAccount)) {
			 throw new CuentaNoPooledException();
		 }
		 
		 if(!(c1.getIban().equals(c2.getIban()))) {
			 throw new CuentasDiferentesException();
		 }
		 
		 
		 //Tras estas comprobaciones, las cuentas tendrian que ser iguales y de tipo pooled

		 PooledAccount pooledEntity = em.find(PooledAccount.class, idUnico);
		 
		 if(pooledEntity == null) {
			 throw new CuentaNoExistenteException();
		 }
		 
		 
		 //Ahora tengo q comprobar si el saldo es valido
		 
		 List<DepositaEn> saldo = pooledEntity.getDepositaEns();
		 
		 for(DepositaEn s: saldo) {
			 
			 if(s.getSaldo() < cantidad) {
				 throw new CuentaSinSaldo0Exception();
			 }
			 
			 if(s.getPooledAccount().equals(pooledEntity) && s.getSaldo()>=cantidad) {
				 Double saldoAntes = s.getSaldo();
				 double total = saldoAntes + origen.getCambioeuro();
				 s.setSaldo(total);
				 
				 //Hay que actualizar el origen
				 
			 }
			 
			 
			 
		 }
		
	}
	
	@Override
	public void cambioDeDivisaAdmin(String idAdmin,Transaccion idUnico,Double cantidad) throws UsuarioNoEsAdministrativoException, CuentasDiferentesException, DivisaNoExistenteException, CuentaNoPooledException, CuentaNoExistenteException, CuentaSinSaldo0Exception {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador != null && administrador.getTipo().equals("N")) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Transaccion transEntity = em.find(Transaccion.class, idUnico);
		
		 Divisa origen = transEntity.getDivisa1();
		 Divisa destino = transEntity.getDivisa2();
		 
		 
		 if(origen == null) {
			 throw new DivisaNoExistenteException();
		 }
		 
		 if(destino == null) {
			 throw new DivisaNoExistenteException();
		 }
		 
		 Cuenta c1 = new Cuenta();
		 c1 = transEntity.getCuenta1();
		 
		 
		 if(!(c1 instanceof PooledAccount) ) {
			 throw new CuentaNoPooledException();
		 }
		 
		 Cuenta c2 = new Cuenta();
		 c2 = transEntity.getCuenta2();
		 
		 if(!(c2 instanceof PooledAccount)) {
			 throw new CuentaNoPooledException();
		 }
		 
		 if(!(c1.getIban().equals(c2.getIban()))) {
			 throw new CuentasDiferentesException();
		 }
		 
		 //Tras estas comprobaciones, las cuentas tendrian que ser iguales y de tipo pooled
		 
		 
		 PooledAccount pooledEntity = em.find(PooledAccount.class, idUnico);
		 
		 if(pooledEntity == null) {
			 throw new CuentaNoExistenteException();
		 }
		 
		 
		 //Ahora tengo q comprobar si el saldo es valido
		 
		 
		 
		 
		 List<DepositaEn> saldo = pooledEntity.getDepositaEns();
		 
		 for(DepositaEn s: saldo) {
			 
			 if(s.getSaldo() < cantidad) {
				 throw new CuentaSinSaldo0Exception();
			 }
			 
			 if(s.getPooledAccount().equals(pooledEntity) && s.getSaldo()>=cantidad) {
				 Double saldoAntes = s.getSaldo();
				 double total = saldoAntes + origen.getCambioeuro();
				 s.setSaldo(total);
				 
				 //Hay que actualizar el origen
				 
			 }
			 
			 
			 
		 }
		 
		 
		
	}
	
	
}
