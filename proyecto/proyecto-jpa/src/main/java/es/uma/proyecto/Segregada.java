package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SEGREGADA database table.
 * 
 */
@Entity
@NamedQuery(name="Segregada.findAll", query="SELECT s FROM Segregada s")
public class Segregada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private String comision;

	//bi-directional one-to-one association to CuentaFintech
	@OneToOne
	@JoinColumn(name="IBAN")
	private CuentaFintech cuentaFintech;

	//bi-directional many-to-one association to CuentaReferencia
	@ManyToOne
	@JoinColumn(name="CUENTA_REFERENCIA_IBAN")
	private CuentaReferencia cuentaReferencia;

	public Segregada() {
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getComision() {
		return this.comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public CuentaFintech getCuentaFintech() {
		return this.cuentaFintech;
	}

	public void setCuentaFintech(CuentaFintech cuentaFintech) {
		this.cuentaFintech = cuentaFintech;
	}

	public CuentaReferencia getCuentaReferencia() {
		return this.cuentaReferencia;
	}

	public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
	}

}