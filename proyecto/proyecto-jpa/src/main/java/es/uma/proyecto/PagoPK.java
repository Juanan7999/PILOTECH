package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PAGOS database table.
 * 
 */
@Embeddable
public class PagoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MOVIMIENTOS_ID", insertable=false, updatable=false)
	private String movimientosId;

	@Column(name="ID_1")
	private String id1;

	public PagoPK() {
	}
	public String getMovimientosId() {
		return this.movimientosId;
	}
	public void setMovimientosId(String movimientosId) {
		this.movimientosId = movimientosId;
	}
	public String getId1() {
		return this.id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PagoPK)) {
			return false;
		}
		PagoPK castOther = (PagoPK)other;
		return 
			this.movimientosId.equals(castOther.movimientosId)
			&& this.id1.equals(castOther.id1);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.movimientosId.hashCode();
		hash = hash * prime + this.id1.hashCode();
		
		return hash;
	}
}