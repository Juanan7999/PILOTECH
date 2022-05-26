package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;

@Named(value = "accionesAutorizada")
@ViewScoped
public class AccionesAutorizada implements Serializable{

	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private String id;
	
	private List<CuentaFintech> cuentas;
	
	private PersonaAutorizada personaAutorizada;
	
	
	public AccionesAutorizada() {
		usuario = new Usuario();
		cuentas = new ArrayList<>();
		personaAutorizada = new PersonaAutorizada();
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
	
	public List<CuentaFintech> getCuentas() throws PersonaAutorizadaNoExistenteException{
		return cuentaEJB.devolverCuentasDeAutorizado(usuario.getCliente().getIdentificacion());
	}
	
	public void setCuentas(List<CuentaFintech> lista) {
		this.cuentas = lista;
	}
	
	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}
	
	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}
	
	
	
}
