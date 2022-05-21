package es.uma.proyecto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


/**
 * The persistent class for the INDIVIDUAL database table.
 * 
 */
@Entity
@NamedQuery(name="Individual.findAll", query="SELECT i FROM Individual i")
public class Individual extends Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;

	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	public Individual() {
	}
	
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Object getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	/*public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}*/
	
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = Date.valueOf(fechaNacimiento);
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Individual [nombre=" + nombre +  ", apellido=" + apellido + "] -> " + super.toString();
	}
}