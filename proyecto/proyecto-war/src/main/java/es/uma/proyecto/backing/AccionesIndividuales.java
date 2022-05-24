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

@Named(value = "accionesIndividuales")
@ViewScoped
public class AccionesIndividuales implements Serializable{

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private String mensaje = "";
	
	private List<Individual> listaCliente;
	
	private Individual individual;

	public AccionesIndividuales() {
		usuario = new Usuario();
		listaCliente = new ArrayList<>();
		individual = new Individual();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public List<Individual> getListaCliente(){
		return clienteEJB.devolverTodosIndividuales();
	}
	
	public void setListaCliente(List<Individual> lista){
		this.listaCliente = lista;
	}
	
	public Individual getIndividual() {
		return this.individual;
	}
	
	public void setIndividual(Individual i) {
		this.individual = i;
	}

	public String baja(String iden) throws InterruptedException {

		try {
			
			usuario = sesion.getUsuario();
			clienteEJB.bajaCliente(usuario, iden);
			
			FacesMessage fm = new FacesMessage(" Baja con exito");
			FacesContext.getCurrentInstance().addMessage("formulario:botonBaja", fm);
			
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
				
				FacesMessage fm = new FacesMessage(" Bloqueo con exito");
				FacesContext.getCurrentInstance().addMessage("formulario:botonBloq", fm);
				
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
			}
			
		return null;
	}
	
	public String desbloquea(String iden) throws InterruptedException {

		try {
			usuario = sesion.getUsuario();
			clienteEJB.activaCliente(usuario, iden);
				
			FacesMessage fm = new FacesMessage(" Desbloqueo con exito");
			FacesContext.getCurrentInstance().addMessage("formulario:botonDesbloq", fm);
				
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
	
	public String modificar(String i) {
		try {
			this.individual = (Individual) clienteEJB.devolverCliente(i);
			System.out.println(individual.getNombre());
		} catch (ClienteNoExistenteException e) {
			
		}
		return "modIndividual.xhtml";
	}
}
