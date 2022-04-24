package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteBloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteDesbloqueadoException;
import es.uma.proyecto.ejb.exceptions.ClienteExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ClienteYaActivoException;
import es.uma.proyecto.ejb.exceptions.ClienteYaDeBajaException;
import es.uma.proyecto.ejb.exceptions.CuentaAbiertaException;


@Local
public interface GestionCliente {

	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona fisica se pide almacenar en la base de datos tambien obligatoriamente nombre y apellidos, y fecha_nacimiento opcionalmente.
	 */
	
	public void altaClienteIndividual(String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteExistenteException; 
	

	
	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona juridica se pide almacenar en la base de datos tambien obligatoriamente razonSocial
	 */
	
	public void altaClienteEmpresa(String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social) throws ClienteExistenteException; 
	
	
	/*
	 * Se encarga de dar de baja a un cliente
	 */
	
	public void bajaCliente(Cliente cliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException;
	
	/*
	 * Se encarga de dar de activar de nuevo a un cliente
	 */
	
	public void activaCliente(Cliente cliente) throws ClienteNoExistenteException, ClienteYaActivoException;
	
	/*
	 * Se encarga de bloquear a un cliente
	 */
	
	public void bloqueaCliente(Cliente cliente) throws ClienteNoExistenteException, ClienteBloqueadoException;
	
	/*
	 * Se encarga de devolver todos los clientes
	 */
	
	public List<Cliente> devolverTodosClientes();
	
}
