package es.uma.proyecto.backing;

import java.io.File;
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
			
			ec.responseReset(); 
		    ec.setResponseContentType("application/CSV"); 
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_fichero + "\""); 
		    File file = new File("C:\\Users\\PC\\Documents\\GitHub\\PILOTECH\\proyecto\\proyecto-war\\target\\generated-sources\\prueba.csv");
			ec.setResponse(file);  
		    fc.responseComplete();
		    
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
				
				ec.responseReset(); 
			    ec.setResponseContentType("application/CSV"); 
			    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_fichero +"\"");
			    File file = new File("C:\\Users\\PC\\Documents\\GitHub\\PILOTECH\\proyecto\\proyecto-war\\target\\generated-sources\\prueba.csv");
				ec.setResponse(file); 
			    fc.responseComplete();
			    
			} catch (UsuarioNoEsAdministrativoException e) {
				FacesMessage fm = new FacesMessage("El usuario no es administrativo");
				FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
			}
	}
	
}
