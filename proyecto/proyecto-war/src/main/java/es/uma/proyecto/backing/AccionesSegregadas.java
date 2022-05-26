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
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.CuentaSinSaldo0Exception;
import es.uma.proyecto.ejb.exceptions.SegregadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Named(value = "accionesSegregadas")
@ViewScoped
public class AccionesSegregadas implements Serializable{

	@Inject
	private GestionCuenta cuentaEJB;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	
	
	private List<Segregada> listaCuenta;
	
	private Segregada seg;

	public AccionesSegregadas() {
		usuario = new Usuario();
		listaCuenta = new ArrayList<>();
		seg = new Segregada();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Segregada> getListaCuenta() {
		return cuentaEJB.devolverTodasSegregadas();
	}

	public void setListaCuenta(List<Segregada> listaCuenta) {
		this.listaCuenta = listaCuenta;
	}

	public Segregada getSeg() {
		return seg;
	}

	public void setSeg(Segregada seg) {
		this.seg = seg;
	}
	
	public String cerrar(String iden) throws InterruptedException {

			try {
				usuario = sesion.getUsuario();
				cuentaEJB.cerrarCuentaSegregada(usuario, seg);
			} catch (UsuarioNoEsAdministrativoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SegregadaNoExistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CuentaSinSaldo0Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
				
		return null;
	}
	
}
