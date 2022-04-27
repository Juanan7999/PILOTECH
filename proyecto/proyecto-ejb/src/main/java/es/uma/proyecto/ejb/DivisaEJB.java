package es.uma.proyecto.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import es.uma.proyecto.ejb.exceptions.CuentaNoPooledException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.DivisaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioEsAdministrativoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class DivisaEJB implements GestionDivisa{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	
	@Override
	public void cambioDeDivisaCliente(String idAdmin,Cliente cliente,PooledAccount cuenta1,PooledAccount cuenta2,Divisa origen,Divisa destino,List<DepositaEn> saldo,Transaccion idUnico) throws UsuarioEsAdministrativoException, ClienteNoExistenteException, ClienteBloqueadoException, ClienteYaDeBajaException, CuentasDiferentesException, DivisaNoExistenteException, CuentaNoPooledException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador!=null || administrador.getTipo().equals("A")) {
			throw new UsuarioEsAdministrativoException();
		}
		
		Cliente clienteEntity = em.find(Cliente.class, cliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}else if (clienteEntity.getEstado().equals("bloqueado")){
			throw new ClienteBloqueadoException();
		}else if (clienteEntity.getEstado().equals("baja")) {
			throw new ClienteYaDeBajaException();
		}
		
		PooledAccount cuenta1Entity = em.find(PooledAccount.class,cuenta1);
		
		PooledAccount cuenta2Entity = em.find(PooledAccount.class, cuenta2);
		
		if(cuenta1Entity.getIban() != cuenta2Entity.getIban()) {
			throw new CuentasDiferentesException();
		}
		
		Divisa origenEntity = em.find(Divisa.class, origen);
		Divisa destinoEntity = em.find(Divisa.class, destino);
		
		if(origenEntity == null) {
			throw new DivisaNoExistenteException();
		}
		
		if(destinoEntity == null) {
			throw new DivisaNoExistenteException();
		}
		
		
		/*
		
		Transaccion transEntity = em.find(Transaccion.class, idUnico);
		
		 origen = transEntity.getDivisa1();
		 destino = transEntity.getDivisa2();
		 
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
		 
		 */
		
		
		
		
	}
	
	@Override
	public void cambioDeDivisaAdmin(String idAdmin,PooledAccount cuenta1,PooledAccount cuenta2,Divisa origen,Divisa destino,List<DepositaEn> saldo,Transaccion idUnico) throws UsuarioNoEsAdministrativoException, CuentasDiferentesException, DivisaNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador == null || !administrador.getTipo().equals("A")) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		PooledAccount cuenta1Entity = em.find(PooledAccount.class,cuenta1);
		
		PooledAccount cuenta2Entity = em.find(PooledAccount.class, cuenta2);
		
		if(cuenta1Entity.getIban() != cuenta2Entity.getIban()) {
			throw new CuentasDiferentesException();
		}
		
		Divisa origenEntity = em.find(Divisa.class, origen);
		Divisa destinoEntity = em.find(Divisa.class, destino);
		
		if(origenEntity == null) {
			throw new DivisaNoExistenteException();
		}
		
		if(destinoEntity == null) {
			throw new DivisaNoExistenteException();
		}
		
		
	}
	
	
}
