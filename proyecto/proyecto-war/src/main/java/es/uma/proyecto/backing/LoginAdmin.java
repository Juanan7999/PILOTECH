package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.UsuarioEJB;
import es.uma.proyecto.ejb.exceptions.Contrase├▒aIncorrectaException;
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
			sesion.setUsuario(usuario);
			return "paginaprincipalAdmin.xhtml";		
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("La cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:botonLogin", fm);
		} catch (Contrase├▒aIncorrectaException e) {
			FacesMessage fm = new FacesMessage("La password no es valida");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:botonLogin", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("loginAdmin:botonLogin", fm);
		}
		
		return null;
	}
}
