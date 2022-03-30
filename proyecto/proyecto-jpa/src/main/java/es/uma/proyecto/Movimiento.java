package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the MOVIMIENTOS database table.
 * 
 */
@Entity
@Table(name="MOVIMIENTOS")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m")
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private BigDecimal cantidad;

	private String concepto;

	@Column(name="FECHA_OPERACION")
	private Object fechaOperacion;

	@Column(name="MODO_OPERACION")
	private String modoOperacion;

	@Column(name="NOMBRE_EMISOR")
	private String nombreEmisor;

	@Column(name="TIPO_EMISOR")
	private String tipoEmisor;

	//bi-directional many-to-one association to Divisa
	@ManyToOne
	private Divisa divisa;

	//bi-directional many-to-one association to Tarjeta
	@ManyToOne
	private Tarjeta tarjeta;

	//bi-directional many-to-one association to Pago
	@OneToMany(mappedBy="movimiento")
	private List<Pago> pagos;

	public Movimiento() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Object getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Object fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getModoOperacion() {
		return this.modoOperacion;
	}

	public void setModoOperacion(String modoOperacion) {
		this.modoOperacion = modoOperacion;
	}

	public String getNombreEmisor() {
		return this.nombreEmisor;
	}

	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}

	public String getTipoEmisor() {
		return this.tipoEmisor;
	}

	public void setTipoEmisor(String tipoEmisor) {
		this.tipoEmisor = tipoEmisor;
	}

	public Divisa getDivisa() {
		return this.divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public Tarjeta getTarjeta() {
		return this.tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public List<Pago> getPagos() {
		return this.pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Pago addPago(Pago pago) {
		getPagos().add(pago);
		pago.setMovimiento(this);

		return pago;
	}

	public Pago removePago(Pago pago) {
		getPagos().remove(pago);
		pago.setMovimiento(null);

		return pago;
	}

}