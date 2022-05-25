package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "altaClienteIndividual")
@RequestScoped
public class AltaClienteIndividual {

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private Individual individual;
	
	
	
	public AltaClienteIndividual() {
		individual = new Individual();
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Individual getIndividual() {
		return individual;
	}
	
	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	
	
	
	public String altaIndividual() {
		try {
			usuario = sesion.getUsuario();
			clienteEJB.altaClienteIndividual(usuario, individual);
			
			FacesMessage fm = new FacesMessage("El cliente ha sido dado de alta con éxito");
			FacesContext.getCurrentInstance().addMessage("altaIndividual:botonAltaIndividual", fm);
			
			return "clientesIndividuales.xhtml";
			
		}catch (ClienteExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("altaIndividual:botonAltaIndividual", fm);
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("altaIndividual:botonAltaIndividual", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("altaIndividual:botonAltaIndividual", fm);
		}
		
		return null;
		
	}
	
	
	
}
