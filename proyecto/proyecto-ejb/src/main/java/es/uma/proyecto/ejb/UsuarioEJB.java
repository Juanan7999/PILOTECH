package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.UsuarioExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsNormalException;

@Default
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
	public Usuario Login(String nombreUsuario, String password) throws UsuarioNoEncontradoException, ContraseñaIncorrectaException, ClienteBloqueadoException, ClienteYaDeBajaException, UsuarioNoEsNormalException {
		Usuario usuarioEntity = em.find(Usuario.class, nombreUsuario);
		
		if(usuarioEntity == null) {
			
			throw new UsuarioNoEncontradoException();
			
		} else if(!usuarioEntity.getPassword().equals(password)) {
			
			throw new ContraseñaIncorrectaException();
			
		}else if(!usuarioEntity.getTipo().equals("N")) {
			
			throw new UsuarioNoEsNormalException();
		}
		
		Cliente clienteUsuario = usuarioEntity.getCliente();
		
		if(clienteUsuario != null) {
		
			if(clienteUsuario.getEstado().equals("bloqueado")) {
				
				throw new ClienteBloqueadoException();
				
			} else if(clienteUsuario.getEstado().equals("baja")) {
				
				throw new ClienteYaDeBajaException();
			}
		}
		
		
		PersonaAutorizada pa = usuarioEntity.getPersonaAutorizada();
		if(pa != null) {
			
			if(pa.getEstado().equals("bloqueado")) {
				
				throw new ClienteBloqueadoException();
				
			} else if(pa.getEstado().equals("baja")) {
				
				throw new ClienteYaDeBajaException();
			}
			
			List<Autorizacion> autorizaciones = pa.getAutorizacions();
			boolean ok = false;
			for(Autorizacion a : autorizaciones) {
				Empresa e = a.getEmpresa();
				if(e.getEstado().equals("activo")) {
					ok =true;
				}
			}
			
			if(!ok) {
				throw new ClienteBloqueadoException();
			}
			
		}
		return usuarioEntity;
	}
	
	@Override
	public Usuario LoginAdmin(String nombreAdmin, String password) throws UsuarioNoEncontradoException,ContraseñaIncorrectaException, UsuarioNoEsAdministrativoException {
		
		
		Usuario adminEntity = em.find(Usuario.class, nombreAdmin);
		
		if(adminEntity == null) {
			throw new UsuarioNoEncontradoException();
		}else if(!adminEntity.getPassword().equals(password)) {
			throw new ContraseñaIncorrectaException();
		}else if(!adminEntity.esAdmin()) {
			throw new UsuarioNoEsAdministrativoException();
		}
		
		return adminEntity;
	}

}
