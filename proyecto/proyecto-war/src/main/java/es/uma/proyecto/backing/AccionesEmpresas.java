package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "accionesEmpresas")
@ViewScoped
public class AccionesEmpresas implements Serializable{

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private List<Empresa> listaCliente;
	
	private Empresa empresa;	

	public AccionesEmpresas() {
		usuario = new Usuario();
		listaCliente = new ArrayList<>();
		empresa = new Empresa();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Empresa> getListaCliente(){
		return clienteEJB.devolverTodosEmpresa();
	}
	
	public void setListaCliente(List<Empresa> lista){
		this.listaCliente = lista;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	

	public String baja(String iden) throws InterruptedException {

		try {
			
			usuario = sesion.getUsuario();
			clienteEJB.bajaCliente(usuario, iden);
			
		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage(" El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
		} catch (ClienteYaDeBajaException e) {
			FacesMessage fm = new FacesMessage(" El cliente ya esta dado de baja");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
		} catch (CuentaAbiertaException e) {
			FacesMessage fm = new FacesMessage(" El cliente tiene alguna cuenta abierta");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage(" El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage(" El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
		}
		
		return null;
	}
	
	public String bloquea(String iden) throws InterruptedException {

			try {
				
				usuario = sesion.getUsuario();
				clienteEJB.bloqueaCliente(usuario, iden);
				
			} catch (ClienteNoExistenteException e) {
				FacesMessage fm = new FacesMessage(" El cliente no existe");
				FacesContext.getCurrentInstance().addMessage("formulario:botonBloq", fm);
			} catch (ClienteBloqueadoException e) {
				FacesMessage fm = new FacesMessage(" El cliente ya esta bloqueado");
				FacesContext.getCurrentInstance().addMessage("formulario:botonBloq", fm);
			} catch (UsuarioNoEsAdministrativoException e) {
				FacesMessage fm = new FacesMessage(" El usuario no es administrativo");
				FacesContext.getCurrentInstance().addMessage("formulario:botonBloq", fm);
			} catch (UsuarioNoEncontradoException e) {
				FacesMessage fm = new FacesMessage(" El usuario no existe");
				FacesContext.getCurrentInstance().addMessage("formulario:botonBloq", fm);
			} catch (ClienteYaDeBajaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
	}
	
	public String desbloquea(String iden) throws InterruptedException {

		try {
			
			usuario = sesion.getUsuario();
			clienteEJB.activaCliente(usuario, iden);
				
		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage(" Cliente no existente");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
		} catch (ClienteYaActivoException e) {
			FacesMessage fm = new FacesMessage(" Cliente ya está activo");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
		} catch (UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage(" El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage(" El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
		} catch (ClienteYaDeBajaException e) {
			FacesMessage fm = new FacesMessage(" El cliente está dado de baja");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
		}
		return null;
	}
	
}
