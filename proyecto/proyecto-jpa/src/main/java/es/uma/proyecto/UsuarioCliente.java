package es.uma.proyecto;

import javax.persistence.*;

@Entity
@DiscriminatorValue("cliente")
public class UsuarioCliente extends Usuario {
	@OneToOne
	@JoinColumn(name = "Cliente", nullable = false)
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return super.toString() + " - UsuarioCliente []";
	}
	
}
