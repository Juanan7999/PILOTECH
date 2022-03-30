package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

//Importacion
/**
 * The persistent class for the AUTORIZACION database table.
 * 
 */
@Entity
@NamedQuery(name="Autorizacion.findAll", query="SELECT a FROM Autorizacion a")
public class Autorizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AutorizacionPK id;

	private BigDecimal tipo;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	private Empresa empresa;

	//bi-directional many-to-one association to PersonaAutorizada
	@ManyToOne
	@JoinColumn(name="PERSONA_AUTORIZADA_ID")
	private PersonaAutorizada personaAutorizada;

	public Autorizacion() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autorizacion other = (Autorizacion) obj;
		return Objects.equals(id, other.id);
	}

	public AutorizacionPK getId() {
		return this.id;
	}

	public void setId(AutorizacionPK id) {
		this.id = id;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PersonaAutorizada getPersonaAutorizada() {
		return this.personaAutorizada;
	}

	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}

}