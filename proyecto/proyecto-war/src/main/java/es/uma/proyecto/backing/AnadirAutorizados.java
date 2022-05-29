package es.uma.proyecto.backing;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoJuridicoException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "anadirAutorizados")
@RequestScoped
public class AnadirAutorizados{

	@Inject
	private GestionCuenta cuentaEJB;

	@Inject
	private GestionCliente clienteEJB;
	
	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	private String identificacion_pa;
	

	

	private Empresa empresa;

	public AnadirAutorizados() {
		usuario = new Usuario();
		empresa = new Empresa();
		identificacion_pa = new String();
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
	
	
	public String getIdentificacion_pa() {
		return identificacion_pa;
	}

	public void setIdentificacion_pa(String p) {
		this.identificacion_pa = p;
	}
	
	public String anadirAut() {
		
		try {
			
			usuario = sesion.getUsuario();
			System.out.println(this.empresa.getIdentificacion());
			
			PersonaAutorizada personaAutorizada = clienteEJB.devolverPersonaAut(identificacion_pa);
			
			System.out.println(personaAutorizada.getIdentificacion());
			
			cuentaEJB.anadirAutorizados(usuario, personaAutorizada, this.empresa.getIdentificacion());
			
			return "clientesEmpresas.xhtml";
			
		}catch(UsuarioNoEsAdministrativoException e) {
			
			FacesMessage fm = new FacesMessage("El usuario no es administrativo");
			FacesContext.getCurrentInstance().addMessage("añadirAutorizados", fm);
			
		}catch(ClienteNoJuridicoException e) {
			
			FacesMessage fm = new FacesMessage("El cliente no es juridico");
			FacesContext.getCurrentInstance().addMessage("añadirAutorizados", fm);
			
		} catch (PersonaAutorizadaNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClienteNoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String accion(String iden) {
			try {
				this.empresa = (Empresa) clienteEJB.devolverClienteEmpresa(iden);
			
				System.out.println(this.empresa.getIdentificacion());
				return "anadirAut.xhtml";
			} catch (ClienteNoExistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	
}
