package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the INDIVIDUAL database table.
 * 
 */
@Entity
@NamedQuery(name="Individual.findAll", query="SELECT i FROM Individual i")
public class Individual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellido;

	@Column(name="FECHA_NACIMIENTO")
	private Object fechaNacimiento;

	private String nombre;

	//bi-directional one-to-one association to Cliente
	@OneToOne
	@JoinColumn(name="ID")
	private Cliente cliente;

	public Individual() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setFechaNacimiento(Object fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}