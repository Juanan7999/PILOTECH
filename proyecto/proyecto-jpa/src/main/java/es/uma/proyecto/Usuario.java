package es.uma.proyecto;

import java.util.Objects;

import javax.persistence.*;

@Entity
public class Usuario {

	/*
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	*/
	
	@Id
	@Column(nullable = false)
	private String nombreUsuario;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String tipo;
	
	@OneToOne
	private PersonaAutorizada personaAutorizada;
	
	@OneToOne
	@JoinColumn(name="USUARIO_CLIENTE", nullable = false)
	private Cliente cliente;

	/*
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	*/

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombreUsuario, other.nombreUsuario);
	}

	@Override
	public String toString() {
		return "Usuario [nombre de usuario= " + nombreUsuario + ", tipo de usuario= "+tipo+"]";
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}

	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}