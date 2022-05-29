package es.uma.proyecto.backing;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cuenta;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;


@Named(value = "mostrarTransacciones")
@RequestScoped
public class MostrarTransacciones {
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	

	private List<Transaccion> transaccionesOrigen;
	
	private List<Transaccion> transaccionesDestino;
	
	private CuentaFintech cuenta;
	
	
	public MostrarTransacciones() {
		
		transaccionesOrigen = new ArrayList<>();
		transaccionesDestino = new ArrayList<>();
		cuenta = new CuentaFintech();
		
	}
	
	public CuentaFintech getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(CuentaFintech cuenta) {
		this.cuenta = cuenta;
	}
	
	public List<Transaccion> getTransaccionesOrigen() throws CuentaNoExistenteException{
		
		return cuentaEJB.getTransaccionesSonOrigen(cuenta.getIban());
			
		
	}
	
	public void setTransaccionesOrigen(List<Transaccion> transaccionesOrigen){
		this.transaccionesOrigen = transaccionesOrigen;
	}
	
	
	public List<Transaccion> getTransaccionesDestino() throws CuentaNoExistenteException{
		
		return cuentaEJB.getTransaccionesSonDestino(cuenta.getIban());
		
	}
	
	public void setTransaccionesDestino(List<Transaccion> transaccionesDestino){
		this.transaccionesDestino = transaccionesDestino;
	}
	
	public String accion(String c) throws CuentaNoExistenteException {
		
		this.cuenta = cuentaEJB.devolverCuenta(c);
		
		return "paginaTransacciones.xhtml";
	}
	
	public String accionA(String c) throws CuentaNoExistenteException {
		
		this.cuenta = cuentaEJB.devolverCuenta(c);
		
		return "paginaTransaccionesAdmin.xhtml";
	}
	
}
