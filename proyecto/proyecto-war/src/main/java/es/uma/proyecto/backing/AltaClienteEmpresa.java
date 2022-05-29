package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import es.uma.proyecto.Empresa;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Named(value = "altaClienteEmpresa")
@RequestScoped
public class AltaClienteEmpresa {

	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	
	private Empresa empresa;
		
	
	public AltaClienteEmpresa() {
		empresa = new Empresa();
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
	
	public String altaEmpresa() {
		try {
			usuario = sesion.getUsuario();
			clienteEJB.altaClienteEmpresa(usuario, empresa);
			return "clientesEmpresas.xhtml";
		}catch (ClienteExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("altaClienteEmpresa:botonAltaEmpresa", fm);
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("altaClienteEmpresa:botonAltaEmpresa", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("altaClienteEmpresa:botonAltaEmpresa", fm);
		}
		
		return null;
		
	}
	
}
