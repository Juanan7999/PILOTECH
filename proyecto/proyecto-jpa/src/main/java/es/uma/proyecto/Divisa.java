package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the DIVISA database table.
 * 
 */
@Entity
@NamedQuery(name="Divisa.findAll", query="SELECT d FROM Divisa d")
public class Divisa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String abreviatura;

	private BigDecimal cambioeuro;

	private String nombre;

	private String simbolo;

	//bi-directional many-to-one association to CuentaReferencia
	@OneToMany(mappedBy="divisa")
	private List<CuentaReferencia> cuentaReferencias;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="divisa")
	private List<Movimiento> movimientos;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="divisa1")
	private List<Transaccion> transaccions1;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="divisa2")
	private List<Transaccion> transaccions2;

	public Divisa() {
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public BigDecimal getCambioeuro() {
		return this.cambioeuro;
	}

	public void setCambioeuro(BigDecimal cambioeuro) {
		this.cambioeuro = cambioeuro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public List<CuentaReferencia> getCuentaReferencias() {
		return this.cuentaReferencias;
	}

	public void setCuentaReferencias(List<CuentaReferencia> cuentaReferencias) {
		this.cuentaReferencias = cuentaReferencias;
	}

	public CuentaReferencia addCuentaReferencia(CuentaReferencia cuentaReferencia) {
		getCuentaReferencias().add(cuentaReferencia);
		cuentaReferencia.setDivisa(this);

		return cuentaReferencia;
	}

	public CuentaReferencia removeCuentaReferencia(CuentaReferencia cuentaReferencia) {
		getCuentaReferencias().remove(cuentaReferencia);
		cuentaReferencia.setDivisa(null);

		return cuentaReferencia;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setDivisa(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setDivisa(null);

		return movimiento;
	}

	public List<Transaccion> getTransaccions1() {
		return this.transaccions1;
	}

	public void setTransaccions1(List<Transaccion> transaccions1) {
		this.transaccions1 = transaccions1;
	}

	public Transaccion addTransaccions1(Transaccion transaccions1) {
		getTransaccions1().add(transaccions1);
		transaccions1.setDivisa1(this);

		return transaccions1;
	}

	public Transaccion removeTransaccions1(Transaccion transaccions1) {
		getTransaccions1().remove(transaccions1);
		transaccions1.setDivisa1(null);

		return transaccions1;
	}

	public List<Transaccion> getTransaccions2() {
		return this.transaccions2;
	}

	public void setTransaccions2(List<Transaccion> transaccions2) {
		this.transaccions2 = transaccions2;
	}

	public Transaccion addTransaccions2(Transaccion transaccions2) {
		getTransaccions2().add(transaccions2);
		transaccions2.setDivisa2(this);

		return transaccions2;
	}

	public Transaccion removeTransaccions2(Transaccion transaccions2) {
		getTransaccions2().remove(transaccions2);
		transaccions2.setDivisa2(null);

		return transaccions2;
	}

}