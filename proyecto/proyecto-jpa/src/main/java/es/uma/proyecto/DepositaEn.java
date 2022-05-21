package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;


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

	@Column(nullable = false)
	private Double saldo;

	//bi-directional many-to-one association to CuentaReferencia
	@ManyToOne
	@JoinColumn(name="CUENTA_REFERENCIA_IBAN", nullable = false)
	@MapsId("cuentaReferenciaIban")
	private CuentaReferencia cuentaReferencia;

	//bi-directional many-to-one association to PooledAccount
	@ManyToOne
	@JoinColumn(name="POOLED_ACCOUNT_IBAN", nullable = false)
	@MapsId("pooledAccountIban")
	private PooledAccount pooledAccount;
	
	public DepositaEn() {
	}

	public DepositaEnPK getId() {
		return this.id;
	}

	public void setId(DepositaEnPK id) {
		this.id = id;
	}

	public Double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
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