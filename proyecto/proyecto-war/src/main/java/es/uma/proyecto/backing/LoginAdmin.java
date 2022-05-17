package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.UsuarioEJB;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "loginAdmin")
@RequestScoped
public class LoginAdmin {

	
	@Inject
	private GestionUsuario usuarioejb;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	
	
	public LoginAdmin() {
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
			usuarioejb.LoginAdmin(usuario.getNombreUsuario(), usuario.getPassword());
			//Aqui para mantener la sesion
			return "paginaprincipal.xhtml";		
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("La cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:user", fm);
		} catch (ContraseñaIncorrectaException e) {
			FacesMessage fm = new FacesMessage("La password no es valida");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:pass", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:tipo", fm);
		}
		
		return null;
		
	}
	
	
	
}
