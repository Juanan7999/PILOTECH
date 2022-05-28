package es.uma.proyecto.backing;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;

@Named(value = "mostrarEmpresas")
@RequestScoped
public class MostrarEmpresas {
	@Inject
	private GestionCuenta cuentaEJB;
	
	@Inject
	private GestionCliente clienteEJB;
	
	private List<Empresa> empresas;
	
	private PersonaAutorizada autorizado;
	
	
	public PersonaAutorizada getAutorizado() {
		return autorizado;
	}



	public void setAutorizado(PersonaAutorizada autorizado) {
		this.autorizado = autorizado;
	}



	public List<Empresa> getEmpresas() throws PersonaAutorizadaNoExistenteException {
		return cuentaEJB.getEmpresasDeAutorizado(autorizado.getIdentificacion());
	}



	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}



	public String accion(String c) throws ClienteNoExistenteException {
		
		this.autorizado =  cuentaEJB.devolverPersonaAutorizada(c);
		
		return "paginaEmpresas.xhtml";
	}
	
}
