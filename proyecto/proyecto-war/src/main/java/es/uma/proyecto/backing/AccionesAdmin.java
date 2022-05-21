package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "accionesAdmin")
@RequestScoped
public class AccionesAdmin {

	
	@Inject
	private GestionCliente clienteEJB;
	
	@Inject
	private InfoSesion sesion;
	
	private Usuario usuario;
	
	private Cliente cliente;
	
	private Individual individual;
	
	private Empresa empresa;
	
	public AccionesAdmin() {
        usuario = new Usuario();
    }
		
	public Usuario getUsuario() {
	    return usuario;
	}

	public void setUsuario(Usuario usuario) {
	   this.usuario = usuario;
	}
	
	
	public String altaClienteIndividual() {
		try {
			clienteEJB.altaClienteIndividual(usuario, cliente);
			return "altaCliente.xhtml";
		} catch (ClienteExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}
		return null;
	}
	
	public String altaClienteEmpresa() {
		
		try {
			clienteEJB.altaClienteEmpresa(usuario, cliente);
			return "altaCliente.xhtml";
		} catch (ClienteExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("botonAltaEmpresa", fm);
		}
		return null;
		
		
		
	}
	
	public String modificarClienteIndividual() {
		
		try {
			clienteEJB.modificarDatosClienteIndividual(usuario, individual.getIdentificacion(), individual);
			return "modificacionCliente.xhtml";
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es admin");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteIndividual", fm);
		}catch(ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteIndividual", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteIndividual", fm);
		}
		return null;	
	}
	
public String modificarClienteEmpresa() {
		
		try {
			clienteEJB.modificarDatosClienteEmpresa(usuario, empresa.getIdentificacion(), empresa);
			return "modificacionCliente.xhtml";
			
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteEmpresa", fm);
		}catch(ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteEmpresa", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no existe");
			FacesContext.getCurrentInstance().addMessage("modificacionClienteEmpresa", fm);
		}
		return null;
	}


public String bajaCliente() {
	
	try {
		clienteEJB.bajaCliente(usuario, cliente.getIdentificacion());
		return "bajaCliente.xhtml";
		
	}catch(ClienteNoExistenteException e) {
		FacesMessage fm = new FacesMessage("El cliente no existe");
		FacesContext.getCurrentInstance().addMessage("bajaCliente", fm);
	}catch(ClienteYaDeBajaException e) {
		FacesMessage fm = new FacesMessage("El cliente ya esta dado de baja");
		FacesContext.getCurrentInstance().addMessage("bajaCliente", fm);
	}catch(CuentaAbiertaException e) {
		FacesMessage fm = new FacesMessage("El cliente no tiene ninguna cuenta abierta");
		FacesContext.getCurrentInstance().addMessage("bajaCliente", fm);
	}catch(UsuarioNoEsAdministrativoException e) {
		FacesMessage fm = new FacesMessage("El usuario no es administrativo");
		FacesContext.getCurrentInstance().addMessage("bajaCliente", fm);
	}catch(UsuarioNoEncontradoException e) {
		FacesMessage fm = new FacesMessage("El usuario no existe");
		FacesContext.getCurrentInstance().addMessage("bajaCliente", fm);
	}
	
	return null;
	
	
}



	
	
	
	
	
	
}
