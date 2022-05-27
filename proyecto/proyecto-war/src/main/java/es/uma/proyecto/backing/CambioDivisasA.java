package es.uma.proyecto.backing;

import java.time.LocalDate;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionDivisa;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.CuentaReferenciaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "cambioDivisas")
@RequestScoped
public class CambioDivisasA {

	@Inject
	private GestionDivisa divisaejb;

	@Inject
	private InfoSesion sesion;

	@Inject
	private GestionCuenta cuentaejb;
	
	private Usuario usuario;

	private PooledAccount pa;

	private String id;

	private String iban_pooled;

	private String iban_cuenta_ref_origen;

	private String iban_cuenta_ref_destino;

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

	public String getIban_cuenta_ref_origen() {
		return iban_cuenta_ref_origen;
	}

	public void setIban_cuenta_ref_origen(String iban_cuenta_ref_origen) {
		this.iban_cuenta_ref_origen = iban_cuenta_ref_origen;
	}

	public String getIban_cuenta_ref_destino() {
		return iban_cuenta_ref_destino;
	}

	public void setIban_cuenta_ref_destino(String iban_cuenta_ref_destino) {
		this.iban_cuenta_ref_destino = iban_cuenta_ref_destino;
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

	public String realizarCambioAdmin() {

		usuario = sesion.getUsuario();
		
		try {
			pa = cuentaejb.devolverPooled(iban_pooled);

			t = new Transaccion();

			t.setIdUnico(1236);
			t.setFechainstruccion(LocalDate.now().toString());

			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;

			cr_origen = cuentaejb.devolverCuentaReferencia(iban_cuenta_ref_origen);
			cr_destino = cuentaejb.devolverCuentaReferencia(iban_cuenta_ref_destino);

			divisaejb.cambioDeDivisaAdmin(usuario, id, pa, cr_origen, cr_destino, this.getCantidadOrigen(), t);
			return "paginaprincipalAdmin.xhtml";
			
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

			t.setIdUnico(1236);
			t.setFechainstruccion(LocalDate.now().toString());

			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;

			cr_origen = cuentaejb.devolverCuentaReferencia(iban_cuenta_ref_origen);
			cr_destino = cuentaejb.devolverCuentaReferencia(iban_cuenta_ref_destino);

			divisaejb.cambioDeDivisaAdmin(usuario, id, pa, cr_origen, cr_destino, this.getCantidadOrigen(), t);
			return "paginaprincipalAdmin.xhtml";
			
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