package es.uma.proyecto;

import java.io.Serializable;


import javax.persistence.*;

import java.sql.Date;

/**
 * The persistent class for the CUENTA_FINTECH database table.
 * 
 */
@Entity
@Table(name="CUENTA_FINTECH")
@NamedQuery(name="CuentaFintech.findAll", query="SELECT c FROM CuentaFintech c")

public class CuentaFintech extends Cuenta implements Serializable{
	private static final long serialVersionUID = 1L;

	

	private String clasificacion;

	@Column(nullable = false)
	private String estado;

	@Column(name="FECHA_APERTURA", nullable = false)
	private Date fechaApertura;

	@Column(name="FECHA_CIERRE")
	private Date fechaCierre;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name = "CUENTA_FINTECH_CLIENTE", nullable = false)
	private Cliente cliente;

	

	public CuentaFintech() {
	}

	

	public String getClasificacion() {
		return this.clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
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

	public Date getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "CuentaFintech [estado=" + estado + ", fechaApertura=" + fechaApertura + "] -> " + super.toString();
	}	
	
}