package es.uma.proyecto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the PERSONA_AUTORIZADA database table.
 * 
 */
@Entity
@Table(name="PERSONA_AUTORIZADA")
@NamedQuery(name="PersonaAutorizada.findAll", query="SELECT p FROM PersonaAutorizada p")
public class PersonaAutorizada implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	*/
	
	@Id
	/*@Column(nullable = false, unique = true)*/
	private String identificacion;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellidos;
	
	@Column(nullable = false)
	private String direccion;

	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	
	private String estado;
	
	private Date fechainicio;

	private Date fechafin;
	
	@OneToOne(mappedBy = "personaAutorizada")
	@JoinColumn(name = "Usuario", nullable = false)
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//bi-directional many-to-one association to Autorizacion
	@OneToMany(mappedBy="personaAutorizada")
	private List<Autorizacion> autorizacions;

	public PersonaAutorizada() {
	}
		
	/*
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	*/
	
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

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
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

	@Override
	public int hashCode() {
		return Objects.hash(identificacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaAutorizada other = (PersonaAutorizada) obj;
		
		return Objects.equals(identificacion, other.identificacion);
	}

	@Override
	public String toString() {
		return "PersonaAutorizada [identificación=" + identificacion + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + "]";
	}
}