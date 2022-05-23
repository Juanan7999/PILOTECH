package es.uma.proyecto.backing;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "añadirAutorizados")
@RequestScoped
public class AñadirAutorizados {

	@Inject
	private GestionCuenta cuentaEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private PersonaAutorizada personaAutorizada;
	
	private CuentaFintech cuentaFintech;
	
	private List<PersonaAutorizada> lpa;
	
	
	public AñadirAutorizados() {
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
	
	public CuentaFintech getCuentaFintech() {
		return cuentaFintech;
	}
	
	public void setCuentaFintech(CuentaFintech cuentaFintech) {
		this.cuentaFintech = cuentaFintech;
	}
	
	public List<PersonaAutorizada> getLPA(){
		return lpa;
	}
	
	public void setLPA(List<PersonaAutorizada> lpa) {
		this.lpa = lpa;
	}
	
	public String anadirAutorizados() {
		
		try {
			
			usuario = sesion.getUsuario();
			cuentaEJB.anadirAutorizados(usuario, lpa, cuentaFintech);
			return "paginaprincipalAdmin.xhtml";
			
		}catch(UsuarioNoEsAdministrativoException e) {
			
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("añadirAutorizados", fm);
			
		}catch(ClienteNoJuridicoException e) {
			
			FacesMessage fm = new FacesMessage("El cliente no es juridico");
			FacesContext.getCurrentInstance().addMessage("añadirAutorizados", fm);
			
		}
		
		return null;
		
	}
	
	
	
	
}
