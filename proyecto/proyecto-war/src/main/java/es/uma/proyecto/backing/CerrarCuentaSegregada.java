package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.SegregadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "cerrarCuentaSegregada")
@RequestScoped
public class CerrarCuentaSegregada {

	@Inject
	InfoSesion sesion;
	
	@Inject
	GestionCuenta cuentaejb;
	
	private String iban ;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String cerrarSegregada() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			
			Segregada s = cuentaejb.devolverSegregada(this.getIban());
			
			cuentaejb.cerrarCuentaSegregada(usuario, s);
			
			return "paginaprincipalAdmin.xhtml";
			
		} catch (SegregadaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta introducida no existe");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaSegregada:iban", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaSegregada:usuario", fm);
		} catch (CuentaSinSaldo0Exception e) {
			FacesMessage fm = new FacesMessage("La cuenta aun tiene saldo");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaSegregada:iban", fm);
		}
		return null;
	}
}
