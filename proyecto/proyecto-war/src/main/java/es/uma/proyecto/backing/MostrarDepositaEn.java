package es.uma.proyecto.backing;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;

@Named(value = "mostrarDepositaEn")
@RequestScoped
public class MostrarDepositaEn {
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	private List<DepositaEn> depositaen;
	



	private PooledAccount pooled;
	
	public List<DepositaEn> getDepositaen() throws CuentaNoExistenteException {
		return cuentaEJB.getDepositadaEnDePooled(pooled.getIban());
	}


	public void setDepositaen(List<DepositaEn> depositaen) {
		this.depositaen = depositaen;
	}

	public PooledAccount getPooled() {
		return pooled;
	}


	public void setPooled(PooledAccount pooled) {
		this.pooled = pooled;
	}
	
	
	
	public String accion(String c) throws CuentaNoExistenteException, PooledNoExistenteException {
		
		this.pooled =  cuentaEJB.devolverPooled(c);
		
		return "paginaDepositaEn.xhtml";
	}
	
	
}
