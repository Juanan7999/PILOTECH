package es.uma.proyecto.backing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "generarInformeAlemania")
@RequestScoped
public class GenerarInformeAlemania {

	@Inject
	InfoSesion sesion;
	
	@Inject
	GestionInforme informeejb;
	
	public void downloadInicial() throws IOException {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();

	    Random rnd = new Random();
	    rnd.nextInt(9999);
	    
	    try {
			
	    	String nombre_fichero = informeejb.generarReporteInicialAlemania(sesion.getUsuario());
			
			ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
		    ec.setResponseContentType("application/CSV"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_fichero + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
			
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
		}
	}
	
	public void downloadSemanal() {
		
		 FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();

		    Random rnd = new Random();
		    rnd.nextInt(9999);
		    
		    try {
				
		    	String nombre_fichero = informeejb.generarReporteInicialAlemania(sesion.getUsuario());
				
				ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
			    ec.setResponseContentType("application/CSV"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
			    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_fichero +"\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
				
			} catch (UsuarioNoEsAdministrativoException e) {
				FacesMessage fm = new FacesMessage("El usuario no es administrativo");
				FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
			}
	}
	
}
