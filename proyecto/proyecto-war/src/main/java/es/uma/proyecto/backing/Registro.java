package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.UsuarioEJB;
import es.uma.proyecto.ejb.exceptions.UsuarioExistenteException;

@Named(value = "registro")
@RequestScoped
public class Registro {

	@Inject
	private UsuarioEJB usuarioejb;

	private Usuario usuario;
	private String repass;

	public Registro() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {

		return usuario;
	}

	public void setUsuario(Usuario usuario) {

		this.usuario = usuario;
	}
	
	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

	public String registrarUsuario() {
		try {
			if (!usuario.getPassword().equals(repass)) {
				FacesMessage fm = new FacesMessage("Las contraseñas deben coincidir");
				FacesContext.getCurrentInstance().addMessage("registro:repass", fm);
				return null;
			}
			
			usuario.setTipo("N");
			
			usuarioejb.creacionUsuario(usuario);
			
			return "paginaprincipal.xhtml";

		} catch (UsuarioExistenteException e) {
			FacesMessage fm = new FacesMessage("Existe un usuario con la misma cuenta");
			FacesContext.getCurrentInstance().addMessage("registro:user", fm);

		}
		return null;
	}
}
