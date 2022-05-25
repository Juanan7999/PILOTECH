package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "altaPersonaAutorizada")
@RequestScoped
public class AltaPersonaAutorizada {

	
	@Inject
	private GestionCuenta cuentaEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private PersonaAutorizada personaAutorizada;
	
	
	public AltaPersonaAutorizada() {
		usuario = new Usuario();
		personaAutorizada = new PersonaAutorizada();
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}
	
	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}
	
	public String altaPersonaAutorizada() {
		
		try {
			usuario = sesion.getUsuario();
			cuentaEJB.altaPersonaAutorizada(usuario, personaAutorizada);
			return null;
			
		}catch(PersonaAutorizadaExistenteException e){
			
			FacesMessage fm = new FacesMessage("La persona autorizada no existe");
			FacesContext.getCurrentInstance().addMessage("altaPersonaAutorizada", fm);
			
		}catch(UsuarioNoEsAdministrativoException e) {
			
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("altaPersonaAutorizada", fm);
			
		}catch(UsuarioNoEncontradoException e) {
		
			FacesMessage fm = new FacesMessage("El usuario no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("altaPersonaAutorizada", fm);
			
		}
		
		
		return null;
		
		
	}
	
}
