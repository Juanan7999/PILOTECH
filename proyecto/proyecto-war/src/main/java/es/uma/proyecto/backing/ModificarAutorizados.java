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
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "modificarAutorizados")
@RequestScoped
public class ModificarAutorizados {

	
	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private GestionCliente clienteEJB;
	
	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private PersonaAutorizada personaAutorizada;
	
	
	public ModificarAutorizados() {
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
	
	public String modificarAutorizado() {
		
		try {
			usuario = sesion.getUsuario();
			cuentaEJB.modificarAutorizados(usuario, personaAutorizada);
			return "personasAutorizadas.xhtml";
		}catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("modificarAutorizados", fm);
		}catch (PersonaAutorizadaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La persona autorizada no existe");
			FacesContext.getCurrentInstance().addMessage("modificarAutorizados", fm);
		}
		
		return null;
		
	}
	
	public String accion(String c) {
		try {
			this.personaAutorizada = clienteEJB.devolverPersonaAut(c);
		} catch (PersonaAutorizadaNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "modAut.xhtml";
	}
	
	
}
