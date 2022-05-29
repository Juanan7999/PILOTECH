package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;


@Named(value = "infoSesion")
@SessionScoped
public class InfoSesion implements Serializable{
	 
	  
	    private Usuario usuario;
	    
	    private List<String> ld;
	    

		private PooledAccount pa;
	    
	    @Inject
	    private GestionCuenta cuentaejb;
	    

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
	    
	    public synchronized List<String> getLd() {
			return ld;
		}

		public synchronized void setLd(List<String> ld) {
			this.ld = ld;
		}
	   
		public synchronized PooledAccount getPa() {
			return pa;
		}

		public synchronized void setPa(PooledAccount pa) {
			this.pa = pa;
		}
		
	    public synchronized String invalidarSesion()
	    {
	        if (usuario != null)
	        {
	            usuario = null;
	            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        }
	        return "index.xhtml";
	    }
	    
	    public synchronized String cambiarDivisa(String c) {
	    	try {
	    		this.pa = cuentaejb.devolverPooled(c);
				this.ld = cuentaejb.getDivisas(c);
				return "paginaDivisas.xhtml";
			} catch (CuentaNoExistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PooledNoExistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
}
