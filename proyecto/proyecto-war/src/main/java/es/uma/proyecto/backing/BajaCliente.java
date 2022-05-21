package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "bajaCliente")
@RequestScoped
public class BajaCliente {

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
		
	private String idCliente;
	
	private String mensaje = "";

	public BajaCliente() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String id_cliente) {
		this.idCliente = id_cliente;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	
	public String baja() {

		try {
			usuario = sesion.getUsuario();
			clienteEJB.bajaCliente(usuario, this.getIdCliente());
			return "paginaprincipalAdmin.xhtml";
		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("baja:cliente", fm);
		} catch (ClienteYaDeBajaException e) {
			FacesMessage fm = new FacesMessage("El cliente ya esta dado de baja");
			FacesContext.getCurrentInstance().addMessage("baja:cliente", fm);
		} catch (CuentaAbiertaException e) {
			FacesMessage fm = new FacesMessage("El cliente no tiene ninguna cuenta abierta");
			FacesContext.getCurrentInstance().addMessage("baja:cliente", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("baja:cliente", fm);
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("baja:cliente", fm);
		}

		return null;
	}
}
