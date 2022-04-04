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

	

	//bi-directional many-to-one association to CuentaReferencia
	@ManyToOne
	@JoinColumn(name="CUENTA_REFERENCIA_IBAN")
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
		return super.toString() + " - Segregada [comision=" + comision + "]";
	}
	
}