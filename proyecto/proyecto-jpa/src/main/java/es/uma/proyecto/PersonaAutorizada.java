package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PERSONA_AUTORIZADA database table.
 * 
 */
@Entity
@Table(name="PERSONA_AUTORIZADA")
@NamedQuery(name="PersonaAutorizada.findAll", query="SELECT p FROM PersonaAutorizada p")
public class PersonaAutorizada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellidos;

	private String direccion;

	private String estado;

	@Column(name="FECHA_NACIMIENTO")
	private Object fechaNacimiento;

	private Object fechafin;

	private Object fechainicio;

	private String identificacion;

	private String nombre;

	//bi-directional many-to-one association to Autorizacion
	@OneToMany(mappedBy="personaAutorizada")
	private List<Autorizacion> autorizacions;

	public PersonaAutorizada() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Object fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Object getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Object fechafin) {
		this.fechafin = fechafin;
	}

	public Object getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Object fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Autorizacion> getAutorizacions() {
		return this.autorizacions;
	}

	public void setAutorizacions(List<Autorizacion> autorizacions) {
		this.autorizacions = autorizacions;
	}

	public Autorizacion addAutorizacion(Autorizacion autorizacion) {
		getAutorizacions().add(autorizacion);
		autorizacion.setPersonaAutorizada(this);

		return autorizacion;
	}

	public Autorizacion removeAutorizacion(Autorizacion autorizacion) {
		getAutorizacions().remove(autorizacion);
		autorizacion.setPersonaAutorizada(null);

		return autorizacion;
	}

}