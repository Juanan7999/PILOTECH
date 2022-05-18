package es.uma.proyecto.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Usuario;

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="proyecto-ejb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
	
		Usuario comprobacion = em.find(Usuario.class, "Juan");
		
		if(comprobacion != null) {
			return;
		}
		
		Usuario usuario_administrativo = new Usuario();
		usuario_administrativo.setNombreUsuario("Juan");
		usuario_administrativo.setPassword("8234");
		usuario_administrativo.setTipo("A");

		em.persist(usuario_administrativo);
	}
}
