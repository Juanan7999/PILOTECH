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
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;


@Local
public interface GestionCliente {

	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona fisica se pide almacenar en la base de datos tambien obligatoriamente nombre y apellidos, y fecha_nacimiento opcionalmente.
	 */
	
	public void altaClienteIndividual(String idAdm, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento, Usuario usuario) throws ClienteExistenteException, UsuarioNoEsAdministrativoException; 
	

	
	/*
	 * Se encarga sencillamente de comprobar en primer lugar que el cliente que se este dando de alta con una Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, 
	 * ciudad, código postal, país, además, al tratarse de una persona juridica se pide almacenar en la base de datos tambien obligatoriamente razonSocial
	 */
	
	public void altaClienteEmpresa(String idAdm, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social) throws ClienteExistenteException, UsuarioNoEsAdministrativoException; 
	
	
	/*
	 * Se encarga de dar de baja a un cliente
	 */
	
	public void bajaCliente(String idAdm, String identificacionCliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException;
	
	/*
	 * Se encarga de dar de activar de nuevo a un cliente
	 */
	
	public void activaCliente(String idAdm, String identificacionCliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException;
	
	/*
	 * Se encarga de bloquear a un cliente
	 */
	
	public void bloqueaCliente(String idAdm, String identificacionCliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException;
	
	/*
	 * Se encarga de devolver todos los clientes
	 */
	
	public List<Cliente> devolverTodosClientes();



	public void modificarDatosClienteIndividual(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta,
			Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre,
			String apellidos, Date fecha_nacimiento, String identificacionCliente) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException;
	
	
	public void modificarDatosClienteEmpresa(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social, String identificacionCliente) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException;



	
}
