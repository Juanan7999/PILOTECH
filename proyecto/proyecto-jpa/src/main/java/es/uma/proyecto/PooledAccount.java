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

@DiscriminatorValue("PooledAccount")

public class PooledAccount extends CuentaFintech implements Serializable {
	private static final long serialVersionUID = 1L;

	

	//bi-directional many-to-one association to DepositaEn
	@OneToMany(mappedBy="pooledAccount")
	private List<DepositaEn> depositaEns;

	

	public PooledAccount() {
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

	@Override
	public String toString() {
		return super.toString() + " - PooledAccount []";
	}
}