package es.uma.proyecto.backing;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
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
	
	//private List<CuentaReferencia> l_cr;
	
	//private List<String> ld;
	
	private List<SelectItem> divisas;

	private Usuario usuario;

	//private PooledAccount pa;

	//private String id;

	//private String ibanPooled;

	private String divisaOrigen;

	private String divisaDestino;

	private double cantidadOrigen;

	@PostConstruct
	public void init() {
		
		
		
		divisas = new ArrayList<SelectItem>();
		for(String d : sesion.getLd()) {
			divisas.add(new SelectItem(d));
		}
	}
	
	public CambioDivisas() {
		
		//pa = new PooledAccount();
		usuario = new Usuario();
		//l_cr = new ArrayList<>();
		//ld = new ArrayList<>();
	}	
	
	public List<SelectItem> getDivisas() {
		return divisas;
	}

	public void setDivisas(List<SelectItem> divisas) {
		this.divisas = divisas;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/*
	public void setPa(PooledAccount p) {
		this.pa = p;
	}*/

	/*
	public PooledAccount getPa() {
		return this.pa;
	}*/
	
	
	/*
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

	public double getCantidadOrigen() {
		return cantidadOrigen;
	}

	public void setCantidadOrigen(double cantidadOrigen) {
		this.cantidadOrigen = cantidadOrigen;
	}

	/*
	public String getIbanPooled() {
		return ibanPooled;
	}

	public void setIbanPooled(String iban_pooled) {
		this.ibanPooled = iban_pooled;
	}*/
	
	public List<CuentaReferencia> getL_cr() {
		try {
			return cuentaejb.getCuentaReferenciasPooled(sesion.getPa().getIban());
		} catch (CuentaNoExistenteException e) {
			System.out.println("Cuenta no existe");
		}
		return null;
	}

	/*public void setL_cr(List<CuentaReferencia> l_cr) {
		this.l_cr = l_cr;
	}*/
	
	/*
	public List<String> getLd() {		
		return ld;
	}

	public void setL_d(List<String> ld) {
		this.ld = ld;
	}*/
	
	public String getDivisaOrigen() {
		return divisaOrigen;
	}

	public void setDivisaOrigen(String divisa_origen) {
		this.divisaOrigen = divisa_origen;
	}

	public String getDivisaDestino() {
		return divisaDestino;
	}

	public void setDivisaDestino(String divisa_destino) {
		this.divisaDestino = divisa_destino;
	}
	
	/*
	public String accion(String c) throws CuentaNoExistenteException {
		
		try {
			this.pa = cuentaejb.devolverPooled(c);
			this.ld = cuentaejb.getDivisas(c);
			System.out.println(this.pa.getIban());
			return "paginaDivisas.xhtml";
		} catch (PooledNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}*/
	
	/*
	public String realizarCambioAdmin() {

		
		
		try {
			
			usuario = sesion.getUsuario();
			
			pa = cuentaejb.devolverPooled(this.ibanPooled);

			t = new Transaccion();

			t.setIdUnico(1239);
			t.setFechainstruccion(LocalDate.now().toString());

			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;

			cr_origen = cuentaejb.devolverCuentaReferencia_Divisa(ibanPooled, this.getDivisa_origen());
			cr_destino = cuentaejb.devolverCuentaReferencia_Divisa(ibanPooled, this.getDivisa_destino());

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
	}*/
	

	public String realizarCambioCliente_Autorizado() {

		usuario = sesion.getUsuario();
		
		try {
			
			CuentaReferencia cr_origen;
			CuentaReferencia cr_destino;
			
			System.out.println(this.divisaOrigen);
			System.out.println(this.divisaDestino);
			
			cr_origen = cuentaejb.devolverCuentaReferencia_Divisa(sesion.getPa().getIban(), divisaOrigen);
			cr_destino = cuentaejb.devolverCuentaReferencia_Divisa(sesion.getPa().getIban(), divisaDestino);

			divisaejb.cambioDeDivisaCliente_Autorizado(sesion.getPa(), cr_origen, cr_destino, this.getCantidadOrigen());
			return "paginaprincipalUsuario.xhtml";
			
		} catch (CuentasDiferentesException e) {
			FacesMessage fm = new FacesMessage("Las cuentas de referencia no pertenecen a la misma Cuenta Pooled");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA:ibanP", fm);
		} catch (ClientePersonaAutorizadaNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA", fm);
		} catch (PooledNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta pooled no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA", fm);
		} catch (SaldoInsuficienteException e) {
			FacesMessage fm = new FacesMessage("La cuenta origen no tiene saldo suficiente");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA", fm);
		} catch (CuentaReferenciaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta de referencia origen o destino no existe");
			FacesContext.getCurrentInstance().addMessage("cambiarDivisaCA", fm);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return null;
	}
}