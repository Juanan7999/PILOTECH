package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Named(value = "modClienteEmpresa")
@RequestScoped
public class ModClienteEmpresa {

	
	@Inject
	private GestionCliente clienteEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private Empresa empresa;
	
	public ModClienteEmpresa() {
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
	
	public String modificarEmpresa() {
		
		try {
			usuario = sesion.getUsuario();
			clienteEJB.modificarDatosClienteEmpresa(usuario, empresa.getIdentificacion(), empresa);
			return "clientesEmpresas.xhtml";
			
		}catch(UsuarioNoEsAdministrativoException e) {
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("modificarEmpresa:botonModificarEmpresa", fm);
		}catch(ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("El cliente no existe");
			FacesContext.getCurrentInstance().addMessage("modClienteEmpresa:botonModificarEmpresa", fm);
		}catch(UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("El usuario no se ha encontrado");
			FacesContext.getCurrentInstance().addMessage("modClienteEmpresa:botonModificarEmpresa", fm);
		}
		
		
		return null;
		
	}
	
	public String accion(String c) {
		try {
			this.empresa = (Empresa) clienteEJB.devolverClienteEmpresa(c);
			System.out.println(empresa.getIdentificacion());
		} catch (ClienteNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "modEmpresa.xhtml";
	}
	
}
