package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TARJETA database table.
 * 
 */
@Entity
@NamedQuery(name="Tarjeta.findAll", query="SELECT t FROM Tarjeta t")
public class Tarjeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long numero;

	private BigDecimal ccv;

	@Column(name="FECHA_ACTIVACION")
	private Object fechaActivacion;

	@Column(name="FECHA_CADUCIDAD")
	private Object fechaCaducidad;

	@Column(name="LIMITE_COBRO_MENSUAL")
	private BigDecimal limiteCobroMensual;

	@Column(name="LIMITE_COBRO_ONLINE")
	private String limiteCobroOnline;

	@Column(name="LIMITE_DISPOSICION_DIARIO")
	private String limiteDisposicionDiario;

	@Column(name="NOMBRE_PROPIETARIO")
	private String nombrePropietario;

	@Column(name="TIPO_TARJETA")
	private String tipoTarjeta;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="tarjeta")
	private List<Movimiento> movimientos;

	//bi-directional many-to-one association to CuentaFintech
	@ManyToOne
	@JoinColumn(name="CUENTA_FINTECH_IBAN")
	private CuentaFintech cuentaFintech;

	public Tarjeta() {
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public BigDecimal getCcv() {
		return this.ccv;
	}

	public void setCcv(BigDecimal ccv) {
		this.ccv = ccv;
	}

	public Object getFechaActivacion() {
		return this.fechaActivacion;
	}

	public void setFechaActivacion(Object fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Object getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Object fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public BigDecimal getLimiteCobroMensual() {
		return this.limiteCobroMensual;
	}

	public void setLimiteCobroMensual(BigDecimal limiteCobroMensual) {
		this.limiteCobroMensual = limiteCobroMensual;
	}

	public String getLimiteCobroOnline() {
		return this.limiteCobroOnline;
	}

	public void setLimiteCobroOnline(String limiteCobroOnline) {
		this.limiteCobroOnline = limiteCobroOnline;
	}

	public String getLimiteDisposicionDiario() {
		return this.limiteDisposicionDiario;
	}

	public void setLimiteDisposicionDiario(String limiteDisposicionDiario) {
		this.limiteDisposicionDiario = limiteDisposicionDiario;
	}

	public String getNombrePropietario() {
		return this.nombrePropietario;
	}

	public void setNombrePropietario(String nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}

	public String getTipoTarjeta() {
		return this.tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setTarjeta(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setTarjeta(null);

		return movimiento;
	}

	public CuentaFintech getCuentaFintech() {
		return this.cuentaFintech;
	}

	public void setCuentaFintech(CuentaFintech cuentaFintech) {
		this.cuentaFintech = cuentaFintech;
	}

}