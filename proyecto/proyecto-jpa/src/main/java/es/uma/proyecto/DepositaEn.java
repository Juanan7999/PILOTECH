package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DEPOSITA_EN database table.
 * 
 */
@Entity
@Table(name="DEPOSITA_EN")
@NamedQuery(name="DepositaEn.findAll", query="SELECT d FROM DepositaEn d")
public class DepositaEn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DepositaEnPK id;

	private BigDecimal saldo;

	//bi-directional many-to-one association to CuentaReferencia
	@ManyToOne
	@JoinColumn(name="CUENTA_REFERENCIA_IBAN")
	private CuentaReferencia cuentaReferencia;

	//bi-directional many-to-one association to PooledAccount
	@ManyToOne
	@JoinColumn(name="POOLED_ACCOUNT_IBAN")
	private PooledAccount pooledAccount;

	public DepositaEn() {
	}

	public DepositaEnPK getId() {
		return this.id;
	}

	public void setId(DepositaEnPK id) {
		this.id = id;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public CuentaReferencia getCuentaReferencia() {
		return this.cuentaReferencia;
	}

	public void setCuentaReferencia(CuentaReferencia cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
	}

	public PooledAccount getPooledAccount() {
		return this.pooledAccount;
	}

	public void setPooledAccount(PooledAccount pooledAccount) {
		this.pooledAccount = pooledAccount;
	}

}