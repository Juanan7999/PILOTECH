package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;


/**
 * The persistent class for the TRANSACCION database table.
 * 
 */
@Entity
@NamedQuery(name="Transaccion.findAll", query="SELECT t FROM Transaccion t")
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_UNICO")
	private long idUnico;

	@Column(nullable = false)
	private Date fechainstruccion;
	
	@Column(nullable = false)
	private Double cantidad;

	private Date fechaejecucion;
	
	@Column(nullable = false)
	private String tipo;
	
	private Double comision;

	private String internacional;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne
	@JoinColumn(name="CUENTA_IBAN", nullable = false)
	private Cuenta cuenta1;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne
	@JoinColumn(name="CUENTA_IBAN2", nullable = false)
	private Cuenta cuenta2;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	@JoinColumn(name="DIVISA_ABREVIATURA", nullable = false)
	private Divisa divisa1;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	@JoinColumn(name="DIVISA_ABREVIATURA2", nullable = false)
	private Divisa divisa2;

	public Transaccion() {
	}

	public long getIdUnico() {
		return this.idUnico;
	}

	public void setIdUnico(long idUnico) {
		this.idUnico = idUnico;
	}

	public Double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getComision() {
		return this.comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Date getFechaejecucion() {
		return this.fechaejecucion;
	}

	public void setFechaejecucion(String fechaejecucion) {
		if(fechaejecucion != null) {
			this.fechaejecucion = Date.valueOf(fechaejecucion);
		}
		
	}

	public Date getFechainstruccion() {
		return this.fechainstruccion;
	}

	public void setFechainstruccion(String fechainstruccion) {
		this.fechainstruccion = Date.valueOf(fechainstruccion);
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

	@Override
	public int hashCode() {
		return Objects.hash(idUnico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaccion other = (Transaccion) obj;
		return idUnico == other.idUnico;
	}

	@Override
	public String toString() {
		return "Transaccion [idUnico=" + idUnico + ", cantidad=" + cantidad + ", fechainstruccion=" + fechainstruccion
				+ ", tipo=" + tipo + "]";
	}
}