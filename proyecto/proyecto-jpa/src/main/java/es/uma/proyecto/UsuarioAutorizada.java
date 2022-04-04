package es.uma.proyecto;

import java.util.Objects;

import javax.persistence.*;

@Entity
@DiscriminatorValue("autorizada")
public class UsuarioAutorizada extends Usuario {
	@OneToOne
	@JoinColumn(name = "Persona Autorizada", nullable = false)
	private PersonaAutorizada personaAutorizada;

	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}

	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}

	@Override
	public String toString() {
		return super.toString() + " - UsuarioAutorizada []";
	}
}
