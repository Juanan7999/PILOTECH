package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.UsuarioExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;

@Stateless
public class UsuarioEJB implements GestionUsuario{

	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	

	@Override
	public List<Usuario> devolverTodosUsuarios(){
		TypedQuery<Usuario> query = em.createQuery("SELECT c FROM Usuario c", Usuario.class);
		List<Usuario> usuarios= query.getResultList();
		return usuarios;
	}
	
	@Override
	public void creacionUsuario(Usuario usuario) throws UsuarioExistenteException {
		
		Usuario usuarioEntity = em.find(Usuario.class, usuario.getNombreUsuario());
		
		if(usuarioEntity != null) {
			throw new UsuarioExistenteException();
		}
		
		
		em.persist(usuario);
	}

	@Override
	public void Login(String nombreUsuario, String password) throws UsuarioNoEncontradoException, ContraseñaIncorrectaException, ClienteBloqueadoException, ClienteYaDeBajaException {
		Usuario usuarioEntity = em.find(Usuario.class, nombreUsuario);
		
		if(usuarioEntity == null) {
			
			throw new UsuarioNoEncontradoException();
			
		} else if(!usuarioEntity.getPassword().equals(password)) {
			
			throw new ContraseñaIncorrectaException();
			
		} 
		
		Cliente clienteUsuario = usuarioEntity.getCliente();
		
		if(clienteUsuario.getEstado().equals("bloqueado")) {
			
			throw new ClienteBloqueadoException();
			
		} else if(clienteUsuario.getEstado().equals("baja")) {
			
			throw new ClienteYaDeBajaException();
			
		}
		
	}
}
