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
	
	private List<PersonaAutorizada> pa;
	

	private PersonaAutorizada personaAutorizada;
	
	public AccionesAutorizada() {
		usuario = new Usuario();
		personaAutorizada = new PersonaAutorizada();
		pa = new ArrayList<>();
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
	
	public List<PersonaAutorizada> getPa() {
		return cuentaEJB.devolverTodosAutorizados();
	}


	public void setPa(List<PersonaAutorizada> pa) {
		this.pa = pa;
	}
	
	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}
	
	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}
	
	
	
}
