package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "eliminarAutorizados")
@RequestScoped
public class EliminarAutorizados {

	
	@Inject
	private GestionCuenta cuentaEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private PersonaAutorizada personaAutorizada;
	
	
	public EliminarAutorizados() {
		personaAutorizada = new PersonaAutorizada();
		usuario = new Usuario();
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
		this.personaAutorizada=personaAutorizada;
	}
	
	
	public String eliminarAutorizado() {
		
		try {
			usuario = sesion.getUsuario();
			cuentaEJB.eliminarAutorizados(usuario, personaAutorizada);
			return "paginaprincipalAdmin.xhtml";
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("eliminarAutorizados", fm);
		}catch(PersonaAutorizadaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La persona autorizada no existe");
			FacesContext.getCurrentInstance().addMessage("eliminarAutorizados", fm);
		}
		
		
		return null;
	}
	
	
}
