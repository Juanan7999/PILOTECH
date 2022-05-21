package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the CUENTA_REFERENCIA database table.
 * 
 */
@Entity
@Table(name="CUENTA_REFERENCIA")
@NamedQuery(name="CuentaReferencia.findAll", query="SELECT c FROM CuentaReferencia c")


public class CuentaReferencia extends Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nombrebanco;

	private String sucursal;
	
	private String pais;

	@Column(nullable = false)
	private Double saldo;

	@Column(name="FECHA_APERTURA")
	private Date fechaApertura;
	
	private String estado;
	
	//bi-directional many-to-one association to Divisa
	@ManyToOne
	@JoinColumn(name= "CUENTA_REFERENCIA_DIVISA", nullable = false)
	private Divisa divisa;

	//bi-directional many-to-one association to DepositaEn
	@OneToMany(mappedBy="cuentaReferencia")
	private List<DepositaEn> depositaEns;

	//bi-directional one-to-one association to Segregada
	@OneToOne
	private Segregada segregada;

	public CuentaReferencia() {
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = Date.valueOf(fechaApertura);
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

	public Double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
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

	public Segregada getSegregadas() {
		return this.segregada;
	}

	public void setSegregadas(Segregada segregadas) {
		this.segregada = segregadas;
	}
	
	//Esto hay que borrarlo ya que segregada ya no es una lista
	/*
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
	*/
	
	@Override
	public String toString() {
		return "CuentaReferencia [nombrebanco=" + nombrebanco + ", saldo=" + saldo + "] -> " + super.toString();
	}	
}