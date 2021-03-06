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
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;

@Named(value = "accionesCliente")
@ViewScoped
public class AccionesCliente implements Serializable{
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private GestionUsuario usuarioEJB;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private String id;
	
	//private Cuenta cuenta;
	
	private List<Segregada> segregadas;
	
	private List<PooledAccount> pooleds;
	
	
	public List<Segregada> getSegregadas() throws ClienteNoExistenteException, PersonaAutorizadaNoExistenteException {
		Individual cliente = usuarioEJB.devolverCliente(sesion.getUsuario());
		if(cliente!=null) {
			
				return cuentaEJB.devolverSegregadasDeIndividual(cliente.getIdentificacion());
			
		}else {
			PersonaAutorizada pa = usuarioEJB.devolverPersonaAut(sesion.getUsuario());
			return cuentaEJB.devolverSegregadasDeAutorizado(pa.getIdentificacion());
		}
		
	}


	public void setSegregadas(List<Segregada> segregadas) {
		this.segregadas = segregadas;
	}


	public List<PooledAccount> getPooleds() throws PersonaAutorizadaNoExistenteException, ClienteNoExistenteException {
		
		Individual cliente = usuarioEJB.devolverCliente(sesion.getUsuario());
		if(cliente!=null) {
			
				return cuentaEJB.devolverPooledDeIndividual(cliente.getIdentificacion());
			
		}else {
			PersonaAutorizada pa = usuarioEJB.devolverPersonaAut(sesion.getUsuario());
			return cuentaEJB.devolverPooledDeAutorizado(pa.getIdentificacion());
		}
	}


	public void setPooleds(List<PooledAccount> pooleds) {
		this.pooleds = pooleds;
	}

	
	


	public AccionesCliente() {
		usuario = new Usuario();
		//cuenta = new Cuenta();
		segregadas = new ArrayList<>();
		pooleds = new ArrayList<>();
		//cliente = new Cliente();
	}
	
	
	public Usuario getUsuario() {
		return sesion.getUsuario();
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
	
	/*public Cuenta getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	*/
	public List<CuentaFintech> getCuentas() throws PersonaAutorizadaNoExistenteException, ClienteNoExistenteException{
		Individual cliente = usuarioEJB.devolverCliente(sesion.getUsuario());
		
		return cuentaEJB.devolverCuentasDeIndividual(cliente.getIdentificacion());
		
	}
	
	/*public void setCuentas(List<CuentaFintech> lista) {
		this.cuentas = lista;
	}
	
	/*public Cliente getCliente() {
		return usuario.getCliente();
	}


	/*public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	/public String devolverCuentas() {
		
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
