package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;

@Named(value = "accionesCliente")
@ViewScoped
public class AccionesCliente implements Serializable{
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private String id;
	
	//private Cuenta cuenta;
	
	private List<CuentaFintech> cuentas;
	
	//private Cliente cliente;


	public AccionesCliente() {
		usuario = sesion.getUsuario();
		//cuenta = new Cuenta();
		cuentas = new ArrayList<>();
		//cliente = new Cliente();
	}
	
	
	public Usuario getUsuario() {
		return sesion.getUsuario();
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
	
	/*public Cuenta getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	*/
	public List<CuentaFintech> getCuentas() throws ClienteNoExistenteException{
		return cuentaEJB.devolverCuentasDeIndividual(usuario.getCliente().getIdentificacion());
	}
	
	public void setCuentas(List<CuentaFintech> lista) {
		this.cuentas = lista;
	}
	
	public Cliente getCliente() {
		return usuario.getCliente();
	}


	/*public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	public String devolverCuentas() {
		
		try {
			usuario = sesion.getUsuario();
			
			return null;
		}catch(ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("accionesCliente", fm);
		}
		
		return null;
		
	}*/
	
	
	

}
