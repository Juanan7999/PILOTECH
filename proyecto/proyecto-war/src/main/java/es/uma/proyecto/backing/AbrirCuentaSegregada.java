package es.uma.proyecto.backing;

import java.time.LocalDate;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaReferenciaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "abrirCuentaSegregada")
@RequestScoped
public class AbrirCuentaSegregada {

	@Inject
	InfoSesion sesion;
	
	@Inject 
	GestionCuenta cuentaejb;
	
	@Inject
	GestionCliente clienteejb;
	
	private Segregada cuenta;
	
	private Usuario usuario;
	
	private String identificacion;
	
	private String iban;
	
	private String iban_referencia;
	
	private String swift;
	
	private String comision;

	public Segregada getCuenta() {
		return cuenta;
	}

	public void setCuenta(Segregada cuenta) {
		this.cuenta = cuenta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}
	
	public String getIban_referencia() {
		return iban_referencia;
	}

	public void setIban_referencia(String iban_referencia) {
		this.iban_referencia = iban_referencia;
	}
	
	public String abrirSegregada() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			
			Cliente cliente = clienteejb.devolverCliente(this.getIdentificacion());
			
			cuenta = new Segregada();
			
			cuenta.setIban(this.getIban());
			cuenta.setSwift(this.getSwift());
			cuenta.setEstado("activa");
			cuenta.setFechaApertura(LocalDate.now().toString());
			cuenta.setFechaCierre(null);
			cuenta.setClasificacion("S");
			cuenta.setComision(this.getComision());
			
			CuentaReferencia c  = cuentaejb.devolverCuentaReferencia(this.getIban_referencia());
			
			cuentaejb.abrirCuentaFintechSegregada(usuario, cuenta, cliente, c);
			
			return "cuentas.xhtml";
			
		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:boton", fm);
		} catch (CuentaReferenciaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta de referencia no existe");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:boton", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:boton", fm);
		}
		
		return null;
	}
}
