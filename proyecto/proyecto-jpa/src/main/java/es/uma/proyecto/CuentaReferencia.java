package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CUENTA_REFERENCIA database table.
 * 
 */
@Entity
@Table(name="CUENTA_REFERENCIA")
@NamedQuery(name="CuentaReferencia.findAll", query="SELECT c FROM CuentaReferencia c")
public class CuentaReferencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private String estado;

	@Column(name="FECHA_APERTURA")
	private Object fechaApertura;

	private String nombrebanco;

	private String pais;

	private BigDecimal saldo;

	private String sucursal;

	//bi-directional one-to-one association to Cuenta
	@OneToOne
	@JoinColumn(name="IBAN")
	private Cuenta cuenta;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	private Divisa divisa;

	//bi-directional many-to-one association to DepositaEn
	@OneToMany(mappedBy="cuentaReferencia")
	private List<DepositaEn> depositaEns;

	//bi-directional many-to-one association to Segregada
	@OneToMany(mappedBy="cuentaReferencia")
	private List<Segregada> segregadas;

	public CuentaReferencia() {
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Object fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public String getNombrebanco() {
		return this.nombrebanco;
	}

	public void setNombrebanco(String nombrebanco) {
		this.nombrebanco = nombrebanco;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public Cuenta getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Divisa getDivisa() {
		return this.divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public List<DepositaEn> getDepositaEns() {
		return this.depositaEns;
	}

	public void setDepositaEns(List<DepositaEn> depositaEns) {
		this.depositaEns = depositaEns;
	}

	public DepositaEn addDepositaEn(DepositaEn depositaEn) {
		getDepositaEns().add(depositaEn);
		depositaEn.setCuentaReferencia(this);

		return depositaEn;
	}

	public DepositaEn removeDepositaEn(DepositaEn depositaEn) {
		getDepositaEns().remove(depositaEn);
		depositaEn.setCuentaReferencia(null);

		return depositaEn;
	}

	public List<Segregada> getSegregadas() {
		return this.segregadas;
	}

	public void setSegregadas(List<Segregada> segregadas) {
		this.segregadas = segregadas;
	}

	public Segregada addSegregada(Segregada segregada) {
		getSegregadas().add(segregada);
		segregada.setCuentaReferencia(this);

		return segregada;
	}

	public Segregada removeSegregada(Segregada segregada) {
		getSegregadas().remove(segregada);
		segregada.setCuentaReferencia(null);

		return segregada;
	}

}