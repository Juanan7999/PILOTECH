package es.uma.proyecto.ejb;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteDesbloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Local
public interface GestionCliente {

	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona fisica se pide almacenar en la base de datos tambien obligatoriamente nombre y apellidos, y fecha_nacimiento opcionalmente.
	 */
	
	public void altaClienteIndividual(Usuario admin, Cliente individual) throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException; 
	

	
	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona juridica se pide almacenar en la base de datos tambien obligatoriamente razonSocial
	 */
	
	public void altaClienteEmpresa(Usuario admin, Cliente empresa) throws ClienteExistenteException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException; 
	
	
	/*
	 * Se encarga de dar de baja a un cliente
	 */
	
	public void bajaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException;
	
	/*
	 * Se encarga de dar de activar de nuevo a un cliente
	 */
	
	public void activaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException;
	
	/*
	 * Se encarga de bloquear a un cliente
	 */
	
	public void bloqueaCliente(Usuario admin, String idCliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException, UsuarioNoEncontradoException;
	
	/*
	 * Se encarga de devolver todos los clientes
	 */
	
	public List<Cliente> devolverTodosClientes();



	public void modificarDatosClienteIndividual(Usuario admin, String identificacion, Individual individual) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException;
	
	
	public void modificarDatosClienteEmpresa(Usuario admin, String identificacion, Empresa empresa) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException, UsuarioNoEncontradoException;


	//public Cliente devolver(String identificacion) throws ClienteNoExistenteException;
	
}
