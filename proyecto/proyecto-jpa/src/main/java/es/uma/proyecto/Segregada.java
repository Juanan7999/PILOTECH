package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SEGREGADA database table.
 * 
 */
@Entity
@NamedQuery(name="Segregada.findAll", query="SELECT s FROM Segregada s")


@DiscriminatorValue("Segregada")

public class Segregada extends CuentaFintech implements Serializable {

	private static final long serialVersionUID = 1L;

	private String comision;
	
	//Aqui he cambiado el many to one a oneToOne
	//bi-directional one-to-one association to CuentaReferencia
	@OneToOne
	@JoinColumn(name = "SEGREGADA_CUENTA_REFERENCIA", nullable = false)
	private CuentaReferencia cuentaReferencia;

	public Segregada() {
	}

	public String getComision() {
		return this.comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public CuentaReferencia getCuentaReferencia() {
		return this.cuentaReferencia;
	}

	public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
	}
	
	@Override
	public String toString() {
		return "Segregada [comision=" + comision + "] -> " + super.toString();
	}
	
}