package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.SegregadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "accionesCuentas")
@ViewScoped
public class AccionesCuentas implements Serializable{

	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private List<Segregada> segregadas;

	private List<PooledAccount> pooleds;
	
	public AccionesCuentas() {
		usuario = new Usuario();
		segregadas = new ArrayList<>();
		pooleds = new ArrayList<>();

	}
	
	public List<Segregada> getSegregadas() {
		return cuentaEJB.devolverTodasSegregadas();
	}

	public void setSegregadas(List<Segregada> segregadas) {
		this.segregadas = segregadas;
	}

	public List<PooledAccount> getPooleds() {
		return cuentaEJB.devolverTodasPooled();
	}

	public void setPooleds(List<PooledAccount> pooleds) {
		this.pooleds = pooleds;
	}
	
	public String cerrarSeg(String iban) {
			
			
			try {
				
				usuario = sesion.getUsuario();
				Segregada s = cuentaEJB.devolverSegregada(iban);
				cuentaEJB.cerrarCuentaSegregada(usuario, s);
				
				return null;
				
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
	
	public String cerrarPool(String iban) {
			
			
			
			try {
				
				usuario = sesion.getUsuario();
				
				PooledAccount pa = cuentaEJB.devolverPooled(iban);
				
				cuentaEJB.cerrarCuentaPooled(usuario, pa);
				
				return null;
				
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
