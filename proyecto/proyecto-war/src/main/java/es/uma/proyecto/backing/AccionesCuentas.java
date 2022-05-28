package es.uma.proyecto.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCuenta;

@Named(value = "accionesSegregadas")
@ViewScoped
public class AccionesCuentas {

	@Inject
	private GestionCuenta cuentaEJB;
	
	private List<Segregada> segregadas;

	private List<PooledAccount> pooleds;
	
	public AccionesCuentas() {
		
		segregadas = new ArrayList<>();
		pooleds = new ArrayList<>();

	}
	
	public List<Segregada> getSegregadas() {
		return cuentaEJB.devolverTodasSegregadas();
	}

	public void setSegregadas(List<Segregada> segregadas) {
		this.segregadas = segregadas;
	}

	public List<PooledAccount> getPooleds() {
		return cuentaEJB.devolverTodasPooled();
	}

	public void setPooleds(List<PooledAccount> pooleds) {
		this.pooleds = pooleds;
	}
	
	
	
}
