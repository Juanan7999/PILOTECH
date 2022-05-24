package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "cerrarCuentaPooled")
@RequestScoped
public class CerrarCuentaPooled {

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
	
	public String cerrarPooled() {
		
		Usuario usuario = sesion.getUsuario();
		
		try {
			
			PooledAccount pa = cuentaejb.devolverPooled(this.getIban());
			
			cuentaejb.cerrarCuentaPooled(usuario, pa);
			
			return "paginaprincipalAdmin.xhtml";
			
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaPooled:usuario", fm);
		} catch (CuentaSinSaldo0Exception e) {
			FacesMessage fm = new FacesMessage("La cuenta aun tiene saldo");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaPooled:iban", fm);
		} catch (PooledNoExistenteException e) {
			FacesMessage fm = new FacesMessage("La cuenta introducida no existe");
			FacesContext.getCurrentInstance().addMessage("cerrarCuentaPooled:iban", fm);
		}
		return null;
	}
}
