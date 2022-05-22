package es.uma.proyecto.backing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.DepositaEnPK;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaReferenciaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledAccountConSolo1CuentaExternaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "abrirCuentaPooled")
@RequestScoped
public class AbrirCuentaPooled {

	@Inject
	InfoSesion sesion;
	
	@Inject 
	GestionCuenta cuentaejb;
	
	@Inject
	GestionCliente clienteejb;
	
	private PooledAccount pa;
	
	private Usuario usuario;
	
	private String identificacion;
	
	private String iban;
	
	private String iban_referencia;
	
	private String swift;
	
	private List<DepositaEn> lista_deposito;
	
	private Double saldo;
	
	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
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
	
	public String getIban_referencia() {
		return iban_referencia;
	}

	public void setIban_referencia(String iban_referencia) {
		this.iban_referencia = iban_referencia;
	}
	
	public String abrirPooled() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			
			Cliente cliente = clienteejb.devolverCliente(this.getIdentificacion());
			
			PooledAccount pa = new PooledAccount();
			pa.setIban(this.getIban());
			pa.setSwift(this.getSwift());
			pa.setEstado("activa");
			pa.setFechaApertura(LocalDateTime.now().toString());
			pa.setFechaCierre(null);
			pa.setClasificacion("P");
			
			CuentaReferencia c  = cuentaejb.devolverCuentaReferencia(iban);
			
			lista_deposito = new ArrayList<>();
			
			DepositaEn deposito = new DepositaEn();
			
			DepositaEnPK deposito_pk = new DepositaEnPK();
			
			deposito_pk.setPooledAccountIban(this.getIban());
			deposito_pk.setCuentaReferenciaIban(this.getIban_referencia());
			
			deposito.setId(deposito_pk);
			
			deposito.setSaldo(this.getSaldo());
			
			lista_deposito.add(deposito);
			
			try {
				cuentaejb.abrirCuentaFintechPooled(usuario, pa, cliente, lista_deposito);
				
			} catch (PooledAccountConSolo1CuentaExternaException e) {
				
			}
			return "paginaprincipalAdmin.xhtml";
			
		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:cliente", fm);
		} catch (CuentaReferenciaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta de referencia no existe");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:ibanReferencia", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("abrirSegregada:boton", fm);
		}
		
		return null;
	}
}
