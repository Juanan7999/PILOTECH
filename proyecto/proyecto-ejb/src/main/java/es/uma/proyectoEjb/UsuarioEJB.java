package es.uma.proyectoEjb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyectoEjb.exceptions.UsuarioExistenteException;
import es.uma.proyectoEjb.exceptions.UsuarioNoEncontradoException;

@Stateless
public class UsuarioEJB implements GestionUsuario{

	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void creacionUsuario(String nombre, String password, String tipo, PersonaAutorizada pA, Cliente cliente)
			throws UsuarioExistenteException {
		Usuario usuario_en_creacion = new Usuario();
		
		Usuario usuarioEntity = em.find(Usuario.class, nombre);
		
		if(usuarioEntity != null) {
			throw new UsuarioExistenteException();
		}
		
		usuario_en_creacion.setNombreUsuario(nombre);
		usuario_en_creacion.setPassword(password);
		usuario_en_creacion.setTipo(tipo);
		
		if(tipo.equals("N")) {
			
			cliente.setUsuario(usuario_en_creacion);
			
		}else if(tipo.equals("J")) {
			
			
			pA.setUsuario(usuario_en_creacion);
		}
		
		em.persist(usuario_en_creacion);
	}

	@Override
	public void Login(String nombreUsuario, String password) throws UsuarioNoEncontradoException {
		Usuario usuario = new Usuario();
		
		Usuario usuarioEntity = em.find(Usuario.class, nombreUsuario);
		
		if(usuarioEntity == null) {
			
			throw new UsuarioNoEncontradoException();
		}
		
	}
}
