package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
	
	public String generarInicial() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			informeejb.generarReporteInicialAlemania(usuario);
			return "paginaprincipalAdmin.xhtml";
			
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("generarInforme:boton1", fm);
		}
		return null;
	}
	
	public String generarSemanal() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			informeejb.generarReporteSemanalAlemania(usuario);
			return "paginaprincipalAdmin.xhtml";
			
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("generarInforme:boton2", fm);
		}
		return null;
	}
	
}
