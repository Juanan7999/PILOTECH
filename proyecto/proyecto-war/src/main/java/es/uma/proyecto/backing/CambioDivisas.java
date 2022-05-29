package es.uma.proyecto.backing;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionDivisa;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaReferenciaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "cambioDivisas")
@RequestScoped
public class CambioDivisas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GestionDivisa divisaejb;

	@Inject
	private InfoSesion sesion;

	@Inject
	private GestionCuenta cuentaejb;
	
	private List<CuentaReferencia> l_cr;
	
	private List<String> l_d;
	
	private Usuario usuario;

	private PooledAccount pa;

	private String id;

	private String iban_pooled;

	private String divisa_origen;

	private String divisa_destino;

	private double cantidadOrigen;

	private Transaccion t;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCantidadOrigen() {
		return cantidadOrigen;
	}

	public void setCantidadOrigen(double cantidadOrigen) {
		this.cantidadOrigen = cantidadOrigen;
	}

	public String getIban_pooled() {
		return iban_pooled;
	}

	public void setIban_pooled(String iban_pooled) {
		this.iban_pooled = iban_pooled;
	}
	
	public List<CuentaReferencia> getL_cr() {
		try {
			return cuentaejb.getCuentaReferenciasPooled(iban_pooled);
		} catch (CuentaNoExistenteException e) {
			System.out.println("Cuenta no existe");
		}
		return null;
	}

	public void setL_cr(List<CuentaReferencia> l_cr) {
		this.l_cr = l_cr;
	}
	
	public List<String> getL_d() {
		
		try {
			l_d = cuentaejb.getDivisas(this.iban_pooled);
		} catch (CuentaNoExistenteException e) {
			System.out.println("La cuenta no existe");
		}
		
		return l_d;
	}

	public void setL_d(List<String> l_d) {
		this.l_d = l_d;
	}
	
	public String getDivisa_origen() {
		return divisa_origen;
	}

	public void setDivisa_origen(String divisa_origen) {
		this.divisa_origen = divisa_origen;
	}

	public String getDivisa_destino() {
		return divisa_destino;
	}

	public void setDivisa_destino(String divisa_destino) {
		this.divisa_destino = divisa_destino;
	}
	
	public CambioDivisas() {
		
		
		l_cr = new ArrayList<>();
		l_d = new ArrayList<>();
	}	
	public String accion(String c) throws CuentaNoExistenteException {
		
		this.iban_pooled = c;
		
		return "paginaDivisas.xhtml";
	}
	
	public String realizarCambioAdmin() {

		usuario = sesion.getUsuario();
		
		try {
			pa = cuentaejb.devolverPooled(iban_pooled);

			t = new Transaccion();

			t.setIdUnico(1239);
			t.setFechainstruccion(LocalDate.now().toString());

			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;

			cr_origen = cuentaejb.devolverCuentaReferencia_Divisa(iban_pooled, this.getDivisa_origen());
			cr_destino = cuentaejb.devolverCuentaReferencia_Divisa(iban_pooled, this.getDivisa_destino());

			divisaejb.cambioDeDivisaAdmin(usuario, id, pa, cr_origen, cr_destino, this.getCantidadOrigen(), t);
			return "paginaDivisas.xhtml";
			
		} catch (CuentasDiferentesException e) {
			FacesMessage fm = new FacesMessage("Las cuentas de referencia no pertenecen a la misma Cuenta Pooled");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:ibanP", fm);
		} catch (ClientePersonaAutorizadaNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:ibanOrigen", fm);
		} catch (PooledNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta pooled no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:iban_pooled", fm);
		} catch (SaldoInsuficienteException e) {
			FacesMessage fm = new FacesMessage("La cuenta origen no tiene saldo suficiente");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:ibanOrigen", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:botonDivisa", fm);
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:botonDivisa", fm);
		} catch (CuentaReferenciaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta de referencia origen o destino no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaAdmin:botonDivisa", fm);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return null;
	}
	
	public String realizarCambioCliente_Autorizado() {

		usuario = sesion.getUsuario();
		
		try {
			
			pa = cuentaejb.devolverPooled(iban_pooled);

			t = new Transaccion();

			t.setIdUnico(1239);
			t.setFechainstruccion(LocalDate.now().toString());

			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;

			cr_origen = cuentaejb.devolverCuentaReferencia_Divisa(iban_pooled, this.getDivisa_origen());
			cr_destino = cuentaejb.devolverCuentaReferencia_Divisa(iban_pooled, this.getDivisa_destino());

			divisaejb.cambioDeDivisaAdmin(usuario, id, pa, cr_origen, cr_destino, this.getCantidadOrigen(), t);
			return "paginaDivisas.xhtml";
			
		} catch (CuentasDiferentesException e) {
			FacesMessage fm = new FacesMessage("Las cuentas de referencia no pertenecen a la misma Cuenta Pooled");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:ibanP", fm);
		} catch (ClientePersonaAutorizadaNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:ibanOrigen", fm);
		} catch (PooledNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta pooled no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:iban_pooled", fm);
		} catch (SaldoInsuficienteException e) {
			FacesMessage fm = new FacesMessage("La cuenta origen no tiene saldo suficiente");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:ibanOrigen", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:botonDivisa", fm);
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:botonDivisa", fm);
		} catch (CuentaReferenciaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta de referencia origen o destino no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:botonDivisa", fm);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return null;
	}
}