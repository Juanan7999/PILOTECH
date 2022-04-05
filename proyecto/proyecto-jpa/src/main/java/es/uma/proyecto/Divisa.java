package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;


/**
 * The persistent class for the DIVISA database table.
 */
@Entity
@NamedQuery(name="Divisa.findAll", query="SELECT d FROM Divisa d")
public class Divisa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String abreviatura;
	
	@Column(nullable = false)
	private String nombre;
	
	private String simbolo;

	@Column(nullable = false)
	private Integer cambioeuro;
	
	public Divisa() {
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getCambioeuro() {
		return this.cambioeuro;
	}

	public void setCambioeuro(Integer cambioeuro) {
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

	@Override
	public int hashCode() {
		return Objects.hash(abreviatura);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Divisa other = (Divisa) obj;
		return Objects.equals(abreviatura, other.abreviatura);
	}

	@Override
	public String toString() {
		return "Divisa [nombre=" + nombre + ", abreviatura=" + abreviatura + ", cambioeuro=" + cambioeuro + "]";
	}
}