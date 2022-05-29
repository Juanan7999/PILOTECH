package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "modClienteIndividual")
@RequestScoped
public class ModClienteIndividual {

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private Individual individual;
	
	
	public ModClienteIndividual() {
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
	
	public String modificarIndividual() {
		
		try {
			usuario = sesion.getUsuario();
			clienteEJB.modificarDatosClienteIndividual(usuario, individual.getIdentificacion(), individual);
			return "clientesIndividuales.xhtml";
			
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("modIndividual:botonModificarIndividual", fm);
		}catch(ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("modIndividual:botonModificarIndividual", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("modIndividual:botonModificarIndividual", fm);
		}		
		
		return null;
		
	}
	
	public String accion(String c) {
		try {
			this.individual = (Individual) clienteEJB.devolverCliente(c);
			System.out.println(individual.getIdentificacion());
		} catch (ClienteNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "modIndividual.xhtml";
	}
	
}
