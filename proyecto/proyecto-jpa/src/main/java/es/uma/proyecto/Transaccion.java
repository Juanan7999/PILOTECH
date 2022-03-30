package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TRANSACCION database table.
 * 
 */
@Entity
@NamedQuery(name="Transaccion.findAll", query="SELECT t FROM Transaccion t")
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_UNICO")
	private long idUnico;

	private BigDecimal cantidad;

	private BigDecimal comision;

	private Object fechaejecucion;

	private Object fechainstruccion;

	private String internacional;

	private String tipo;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne
	@JoinColumn(name="CUENTA_IBAN")
	private Cuenta cuenta1;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne
	@JoinColumn(name="CUENTA_IBAN2")
	private Cuenta cuenta2;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	@JoinColumn(name="DIVISA_ABREVIATURA")
	private Divisa divisa1;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	@JoinColumn(name="DIVISA_ABREVIATURA2")
	private Divisa divisa2;

	public Transaccion() {
	}

	public long getIdUnico() {
		return this.idUnico;
	}

	public void setIdUnico(long idUnico) {
		this.idUnico = idUnico;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getComision() {
		return this.comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public Object getFechaejecucion() {
		return this.fechaejecucion;
	}

	public void setFechaejecucion(Object fechaejecucion) {
		this.fechaejecucion = fechaejecucion;
	}

	public Object getFechainstruccion() {
		return this.fechainstruccion;
	}

	public void setFechainstruccion(Object fechainstruccion) {
		this.fechainstruccion = fechainstruccion;
	}

	public String getInternacional() {
		return this.internacional;
	}

	public void setInternacional(String internacional) {
		this.internacional = internacional;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cuenta getCuenta1() {
		return this.cuenta1;
	}

	public void setCuenta1(Cuenta cuenta1) {
		this.cuenta1 = cuenta1;
	}

	public Cuenta getCuenta2() {
		return this.cuenta2;
	}

	public void setCuenta2(Cuenta cuenta2) {
		this.cuenta2 = cuenta2;
	}

	public Divisa getDivisa1() {
		return this.divisa1;
	}

	public void setDivisa1(Divisa divisa1) {
		this.divisa1 = divisa1;
	}

	public Divisa getDivisa2() {
		return this.divisa2;
	}

	public void setDivisa2(Divisa divisa2) {
		this.divisa2 = divisa2;
	}

}