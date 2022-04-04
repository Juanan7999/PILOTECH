package es.uma.proyecto;

import javax.persistence.*;
@Entity
@DiscriminatorValue("administrativo")
public class UsuarioAdmin extends Usuario{

	@Override
	public String toString() {
		return super.toString() + " - UsuarioAdmin []";
	}
}
