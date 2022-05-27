package es.uma.proyecto.backing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import org.primefaces.shaded.commons.io.IOUtils;
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
	    int numero = rnd.nextInt(9999);
	   
	    String  nombre_archivo_descargado = new String("FINTECH_IBAN_1_" + numero + ".csv");
	    
	    try {
			
	    	Path ruta = informeejb.generarReporteInicialAlemania(sesion.getUsuario());
			ec.responseReset(); 
		    ec.setResponseContentType("application/CSV"); 
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_archivo_descargado + "\""); 
		    
		    try(FileInputStream f = new FileInputStream(ruta.toString())){
		    	try(OutputStream output = ec.getResponseOutputStream()){
		    	
		    		IOUtils.copy(f, output);
		    		fc.responseComplete();
		    	}
		    	
		    }catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
		}
	    
	}
	
	public void downloadSemanal() {
		
		 FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();

		    Random rnd = new Random();
		    int numero = rnd.nextInt(9999);
		    
		    String  nombre_archivo_descargado = new String("FINTECH_IBAN_2_" + numero + ".csv");
		    
		    try {
				
		    	Path ruta = informeejb.generarReporteSemanalAlemania(sesion.getUsuario());
				ec.responseReset(); 
			    ec.setResponseContentType("application/CSV"); 
			    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombre_archivo_descargado + "\""); 
			    
			    try(FileInputStream f = new FileInputStream(ruta.toString())){
			    	try(OutputStream output = ec.getResponseOutputStream()){
			    	
			    		IOUtils.copy(f, output);
			    	}
			    } catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			    fc.responseComplete();
			    
			} catch (UsuarioNoEsAdministrativoException e) {
				FacesMessage fm = new FacesMessage("El usuario no es administrativo");
				FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
			}
	}
}
