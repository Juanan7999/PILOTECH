package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionDivisa;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "cambioDivisasCAut")
@RequestScoped
public class CambioDivisasCAut {
	@Inject
	private GestionDivisa divisaEJB;

	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private String id;
	
	private PooledAccount cuentaP;
	
	private CuentaReferencia origen;
	
	private CuentaReferencia destino;
	
	private double cantidadOrigen;
	
	private Transaccion t;
	
	public CambioDivisasCAut() {
		usuario = new Usuario();
		cuentaP = new PooledAccount();
		origen = new CuentaReferencia();
		destino = new CuentaReferencia();
		t = new Transaccion();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public PooledAccount getPooledAccount() {
		return cuentaP;
	}
	
	public void setPooledAccount(PooledAccount cuentaP) {
		this.cuentaP = cuentaP;
	}
	
	
	public CuentaReferencia getOrigen() {
		return origen;
	}
	
	public void setOrigen(CuentaReferencia origen) {
		this.origen = origen;
	}
	
	
	public CuentaReferencia getDestino() {
		return destino;
	}
	
	public void setDestino(CuentaReferencia destino) {
			this.destino = destino;
			
	}
	
	public Double getCantidadOrigen() {
		return cantidadOrigen;
	}
	
	public void setCantidadOrigen(Double cantidadOrigen) {
		this.cantidadOrigen = cantidadOrigen;
	}
	
	public Transaccion getTransaccion() {
		return t;
	}
	
	public void setTransaccion(Transaccion t) {
		this.t = t;
	}
	
	public String getIbanOrigen() {
		return origen.getIban();
	}
	
	public String getIbanDestino() {
		return destino.getIban();
	}
	
	public String cambioDivisaAdmin() {
		
		try {
			
			usuario = sesion.getUsuario();
			divisaEJB.cambioDeDivisaCliente_Autorizado(id, cuentaP, origen, destino, getCantidadOrigen(), t);
			return "paginaprincipalAdmin.xhtml"; 
		}catch(CuentasDiferentesException e) {
			
			FacesMessage fm = new FacesMessage("Las cuentas son diferentes");
			FacesContext.getCurrentInstance().addMessage("cambioDivisasA", fm);
			
		}catch(ClientePersonaAutorizadaNoEncontradoException e) {
			
			FacesMessage fm = new FacesMessage("El cliente o persona autorizada no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("cambioDivisasA", fm);
			
		}catch(PooledNoExistenteException e) {
			
			FacesMessage fm = new FacesMessage("La PooledAccount no existe");
			FacesContext.getCurrentInstance().addMessage("cambioDivisasA", fm);
			
		}catch(SaldoInsuficienteException e) {
			
			FacesMessage fm = new FacesMessage("El saldo es insuficiente");
			FacesContext.getCurrentInstance().addMessage("cambioDivisasA", fm);
			
		}
		
		return null;
	}
	
}
