package es.uma.proyecto.ejb;


import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Cliente;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;

import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;

import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class DivisaEJB implements GestionDivisa {

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void cambioDeDivisaCliente_Autorizado(String id, PooledAccount cuentaP, CuentaReferencia origen,
			CuentaReferencia destino, Double cantidadOrigen, Transaccion t) throws 
			CuentasDiferentesException, ClientePersonaAutorizadaNoEncontradoException, PooledNoExistenteException, SaldoInsuficienteException {

		Cliente c = em.find(Cliente.class, id);

		if (c == null) {

			PersonaAutorizada pa = em.find(PersonaAutorizada.class, id);

			if (pa == null) {

				throw new ClientePersonaAutorizadaNoEncontradoException();
			}
		}

		PooledAccount pooled = em.find(PooledAccount.class, cuentaP.getIban());

		if (pooled == null) {

			throw new PooledNoExistenteException();
		}

		if (!cuentaP.getDepositaEns().contains(origen) || !cuentaP.getDepositaEns().contains(destino)) {
			throw new CuentasDiferentesException();
		}
		
		if(origen.getSaldo() < cantidadOrigen || destino.getSaldo() == 0) {
		
			throw new SaldoInsuficienteException();	
		}
		
		origen.setSaldo(origen.getSaldo()-cantidadOrigen);
		Double cantidadEnEuros = cantidadOrigen*origen.getDivisa().getCambioeuro();
		Double cantidadEnDivisaDestino = cantidadEnEuros/destino.getDivisa().getCambioeuro();
		destino.setSaldo(destino.getSaldo()+cantidadEnDivisaDestino);
	
		for(DepositaEn dp : cuentaP.getDepositaEns()) {
			
			if(dp.getCuentaReferencia().equals(origen)) {
				
				dp.setSaldo(dp.getSaldo()-cantidadOrigen);
				
			}else if(dp.getCuentaReferencia().equals(destino)) {
				
				dp.setSaldo(dp.getSaldo()+cantidadEnDivisaDestino);
			}
		}
		
		t.setCuenta1(origen);
		t.setCuenta2(destino);
		t.setCantidad(cantidadOrigen*origen.getDivisa().getCambioeuro());
		t.setDivisa1(origen.getDivisa());
		t.setDivisa2(origen.getDivisa());
		t.setTipo("CD");
		
		em.merge(t);
	}

	@Override
	public void cambioDeDivisaAdmin(String idAdmin, String id, PooledAccount cuentaP, CuentaReferencia origen,
			CuentaReferencia destino, Double cantidadOrigen, Transaccion t) throws 
			CuentasDiferentesException, ClientePersonaAutorizadaNoEncontradoException, PooledNoExistenteException, SaldoInsuficienteException, UsuarioNoEsAdministrativoException {
		
		Usuario admin = em.find(Usuario.class, idAdmin);
		
		if(admin == null) {
			
			throw new UsuarioNoEsAdministrativoException();
		}
		
		
		Cliente c = em.find(Cliente.class, id);

		if (c == null) {

			PersonaAutorizada pa = em.find(PersonaAutorizada.class, id);

			if (pa == null) {

				throw new ClientePersonaAutorizadaNoEncontradoException();
			}
		}

		PooledAccount pooled = em.find(PooledAccount.class, cuentaP.getIban());

		if (pooled == null) {

			throw new PooledNoExistenteException();
		}

		
		boolean esIgual1 = false;
		boolean esIgual2 = false;
		
		for(DepositaEn dp : origen.getDepositaEns()) {
			
			
			if(dp.getPooledAccount().getIban().equals(pooled.getIban())) {
				
				esIgual1 = true;
			}
		}
		
		for(DepositaEn dp : destino.getDepositaEns()) {
			
			
			if(dp.getPooledAccount().getIban().equals(pooled.getIban())) {
				
				esIgual2 = true;
			}
		}
		
		if(!(esIgual1 && esIgual2)) {
			
			throw new CuentasDiferentesException();
		}
		
		if(origen.getSaldo() < cantidadOrigen || destino.getSaldo() != 0) {
		
			throw new SaldoInsuficienteException();	
		}
		
		origen.setSaldo(origen.getSaldo()-cantidadOrigen);
		Double cantidadEnEuros = cantidadOrigen*origen.getDivisa().getCambioeuro();
		Double cantidadEnDivisaDestino = cantidadEnEuros/destino.getDivisa().getCambioeuro();
		destino.setSaldo(destino.getSaldo()+cantidadEnDivisaDestino);
	
		for(DepositaEn dp : cuentaP.getDepositaEns()) {
			
			if(dp.getCuentaReferencia().equals(origen)) {
				
				dp.setSaldo(dp.getSaldo()-cantidadOrigen);
				
			}else if(dp.getCuentaReferencia().equals(destino)) {
				
				dp.setSaldo(dp.getSaldo()+cantidadEnDivisaDestino);
			}
		}
		
		t.setCuenta1(origen);
		t.setCuenta2(destino);
		t.setCantidad(cantidadOrigen*origen.getDivisa().getCambioeuro());
		t.setDivisa1(origen.getDivisa());
		t.setDivisa2(origen.getDivisa());
		t.setTipo("CD");
		
		em.merge(t);
	}

}
