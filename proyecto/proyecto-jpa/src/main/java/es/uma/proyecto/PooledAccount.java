package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the POOLED_ACCOUNT database table.
 * 
 */
@Entity
@Table(name="POOLED_ACCOUNT")
@NamedQuery(name="PooledAccount.findAll", query="SELECT p FROM PooledAccount p")
public class PooledAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	//bi-directional many-to-one association to DepositaEn
	@OneToMany(mappedBy="pooledAccount")
	private List<DepositaEn> depositaEns;

	//bi-directional one-to-one association to CuentaFintech
	@OneToOne
	@JoinColumn(name="IBAN")
	private CuentaFintech cuentaFintech;

	public PooledAccount() {
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public List<DepositaEn> getDepositaEns() {
		return this.depositaEns;
	}

	public void setDepositaEns(List<DepositaEn> depositaEns) {
		this.depositaEns = depositaEns;
	}

	public DepositaEn addDepositaEn(DepositaEn depositaEn) {
		getDepositaEns().add(depositaEn);
		depositaEn.setPooledAccount(this);

		return depositaEn;
	}

	public DepositaEn removeDepositaEn(DepositaEn depositaEn) {
		getDepositaEns().remove(depositaEn);
		depositaEn.setPooledAccount(null);

		return depositaEn;
	}

	public CuentaFintech getCuentaFintech() {
		return this.cuentaFintech;
	}

	public void setCuentaFintech(CuentaFintech cuentaFintech) {
		this.cuentaFintech = cuentaFintech;
	}

}