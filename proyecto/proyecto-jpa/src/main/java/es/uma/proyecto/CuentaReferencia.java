package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CUENTA_REFERENCIA database table.
 * 
 */
@Entity
@Table(name="CUENTA_REFERENCIA")
@NamedQuery(name="CuentaReferencia.findAll", query="SELECT c FROM CuentaReferencia c")


@DiscriminatorValue("CuentaReferencia")

public class CuentaReferencia extends Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;



	private String estado;

	@Column(name="FECHA_APERTURA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaApertura;

	@Column(nullable = false)
	private String nombrebanco;

	private String pais;

	@Column(nullable = false)
	private Double saldo;

	private String sucursal;

	

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

	

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
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