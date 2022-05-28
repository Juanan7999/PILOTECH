package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteDesbloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

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
	
	public String bloquea(String iden) throws InterruptedException {
			
			try {
				usuario = sesion.getUsuario();
				cuentaEJB.bloqueaAutorizado(usuario, iden);
			} catch (PersonaAutorizadaNoExistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClienteBloqueadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UsuarioNoEsAdministrativoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UsuarioNoEncontradoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
	}
	
	public String desbloquea(String iden) {
		
		
		try {
			usuario = sesion.getUsuario();
			cuentaEJB.desbloqueaAutorizado(usuario, iden);
		} catch (PersonaAutorizadaNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClienteDesbloqueadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoEsAdministrativoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
