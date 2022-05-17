package es.uma.proyecto.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.ClienteEJB;
import es.uma.proyecto.ejb.UsuarioEJB;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioExistenteException;

@Named(value = "registro")
@RequestScoped
public class Registro {

	@Inject
	private UsuarioEJB usuarioejb;

	@Inject
	private ClienteEJB clienteejb;

	@Inject
	private ClienteEJB personaAutejb;

	private Usuario usuario;
	private String repass;
	private String identificacion;
	private String tipo;

	public Registro() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {

		return usuario;
	}

	public void setUsuario(Usuario usuario) {

		this.usuario = usuario;
	}

	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String registrarUsuario() {
		try {
			if (!usuario.getPassword().equals(repass)) {
				FacesMessage fm = new FacesMessage("Las contraseñas deben coincidir");
				FacesContext.getCurrentInstance().addMessage("registro:repass", fm);
				return null;
			}

			if (tipo.equals("Cliente")) {

				Cliente cliente = clienteejb.devolverCliente(identificacion);

				if (cliente != null) {

					usuario.setCliente(cliente);

				}
				usuario.setTipo("N");

				usuarioejb.creacionUsuario(usuario);
				return "index.xhtml";

			} else {

				PersonaAutorizada pA = personaAutejb.devolverPersonaAut(identificacion);

				if (pA != null) {

					usuario.setPersonaAutorizada(pA);
				}
				usuario.setTipo("A");

				usuarioejb.creacionUsuario(usuario);
				return "index.xhtml";
			}

		} catch (UsuarioExistenteException e) {
			FacesMessage fm = new FacesMessage("Existe un usuario con la misma cuenta");
			FacesContext.getCurrentInstance().addMessage("registro:user", fm);

		} catch (ClienteNoExistenteException e) {
			FacesMessage fm = new FacesMessage("No existe un cliente con los datos introducidos");
			FacesContext.getCurrentInstance().addMessage("registro:iden", fm);
		} catch (PersonaAutorizadaNoExistenteException e) {
			FacesMessage fm = new FacesMessage("No existe una persona autorizada con los datos introducidos");
			FacesContext.getCurrentInstance().addMessage("registro:tipo_usuario", fm);
		}
		return null;
	}

}
