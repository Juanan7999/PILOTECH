package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.UsuarioEJB;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsNormalException;
import es.uma.proyecto.Individual;
import javax.*;

@Named(value = "loginNormal")
@RequestScoped
public class LoginNormal {
       
	@Inject
	private GestionUsuario usuarioejb;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	public LoginNormal() {
        usuario = new Usuario();
    }
		
	public Usuario getUsuario() {
	    return usuario;
	}

	public void setUsuario(Usuario usuario) {
	   this.usuario = usuario;
	}
	
    public String login() {
    	 try {		
    		sesion.setUsuario(usuario);
			usuarioejb.Login(usuario.getNombreUsuario(), usuario.getPassword());
			return "paginaprincipalUsuario.xhtml";	
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("La cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("login:user", fm);
		} catch (ContraseñaIncorrectaException e) {
			FacesMessage fm = new FacesMessage("Contraseña incorrecta");
			FacesContext.getCurrentInstance().addMessage("login:pass", fm);        
		} catch (ClienteBloqueadoException e) {
			FacesMessage fm = new FacesMessage("El cliente está bloqueado");
		    FacesContext.getCurrentInstance().addMessage("login:user", fm);
		} catch (ClienteYaDeBajaException e) {
			FacesMessage fm = new FacesMessage("El cliente no está activo");
		    FacesContext.getCurrentInstance().addMessage("login:user", fm);		
		} catch (UsuarioNoEsNormalException e) {
			FacesMessage fm = new FacesMessage("El usuario es admin");
		    FacesContext.getCurrentInstance().addMessage("login:botonLogin", fm);	
		}   		
    	return null;
    }
}