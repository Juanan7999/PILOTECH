package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsNormalException;


@Local
public interface GestionUsuario {
	
	/**
	 * Este metodo se encarga de comprobar que el usuario exista. En caso de que exista, se creará el usuario con
	 * sus atributos nombreUsuario, password, tipo y además se comprobará si se trata de un cliente como persona física o
	 * un cliente como persona juridica. Una de estas opciones implica a enlazar y establecer que tipo de cliente y si se trata 
	 * tambien de una persona autorizada
	 */
	
	public void creacionUsuario(Usuario usuario) throws UsuarioExistenteException;
	
	
	/**
	 * El siguiente metodo comprueba que el usuario se encuentre en la base de datos. En caso de que no exista se lanza
	 * una excepción.
	 * @throws ClienteBloqueadoException 
	 * @throws ClienteYaDeBajaException 
	 */
	
	public Usuario Login(String nombreUsuario, String password) throws UsuarioNoEncontradoException, ContraseñaIncorrectaException, ClienteBloqueadoException, ClienteYaDeBajaException, UsuarioNoEsNormalException;


	public List<Usuario> devolverTodosUsuarios();
	
	public Usuario LoginAdmin(String nombreAdmin, String password) throws UsuarioNoEncontradoException, ContraseñaIncorrectaException, UsuarioNoEsAdministrativoException, UsuarioNoEsAdministrativoException;


	Individual devolverCliente(Usuario u);


	PersonaAutorizada devolverPersonaAut(Usuario u);
	
	
		
}
