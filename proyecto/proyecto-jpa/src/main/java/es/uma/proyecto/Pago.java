package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PAGOS database table.
 * 
 */
@Entity
@Table(name="PAGOS")
@NamedQuery(name="Pago.findAll", query="SELECT p FROM Pago p")
public class Pago implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PagoPK id;

	private BigDecimal cantidad;

	private BigDecimal cuotas;

	//bi-directional many-to-one association to Movimiento
	@ManyToOne
	@JoinColumn(name="MOVIMIENTOS_ID")
	private Movimiento movimiento;

	public Pago() {
	}

	public PagoPK getId() {
		return this.id;
	}

	public void setId(PagoPK id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getCuotas() {
		return this.cuotas;
	}

	public void setCuotas(BigDecimal cuotas) {
		this.cuotas = cuotas;
	}

	public Movimiento getMovimiento() {
		return this.movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

}