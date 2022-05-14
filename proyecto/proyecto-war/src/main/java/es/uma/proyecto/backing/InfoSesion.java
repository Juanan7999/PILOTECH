package es.uma.proyecto.backing;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;


@Named(value = "infoSesion")
@SessionScoped
public class InfoSesion {
	 
	  
	    private Usuario usuario;
	    
	    /**
	     * Creates a new instance of InfoSesion
	     */
	    public InfoSesion() {
	    }

	    public synchronized void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }

	    public synchronized Usuario getUsuario() {
	        return usuario;
	    }
	    
	   
	    public synchronized String invalidarSesion()
	    {
	        if (usuario != null)
	        {
	            usuario = null;
	            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        }
	        return "login.xhtml";
	    }
}
