package es.uma.proyecto.backing;


import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;


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
	
    public String registrarUsuario() {
        try {
            if (!usuario.getPassword().equals(repass)) {
                FacesMessage fm = new FacesMessage("Las contraseñas deben coincidir");
                FacesContext.getCurrentInstance().addMessage("registro:repass", fm);
                return null;
            }

            usuarioejb.creacionUsuario(usuario);
            
            
        } catch (UsuarioExistenteException e) {
            FacesMessage fm = new FacesMessage("Existe un usuario con la misma cuenta");
            FacesContext.getCurrentInstance().addMessage("registro:user", fm);
            
        }
        return null;
    }
}
