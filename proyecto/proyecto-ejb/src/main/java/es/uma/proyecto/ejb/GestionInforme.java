package es.uma.proyecto.ejb;

import java.nio.file.Path;
import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Local
public interface GestionInforme {
	
	public List<Segregada>devolverInformeHolandaProductoTodas(String IBAN) throws CuentaNoExistenteException ;
	
	public List<Segregada> devolverInformeHolandaProductoInactivas(String IBAN) throws CuentaNoExistenteException;
	
	public List<Segregada> devolverInformeHolandaProductoActivas(String IBAN) throws CuentaNoExistenteException;
	
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta, Date fechaBaja) ;
	
	/*
	 * Este método se encarga de generar el reporte regulatorio de Alemania que se realiza inicialmente 
	 * con todas las cuentas que se han proporcionado sin importar el estado de la cuenta o el cliente.
	 * 
	 */
	
	public Path generarReporteInicialAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException ;
	
	/*
	 * Este método se encarga de generar el reporte regulatorio de Alemania que se realiza semanalmente 
	 * con todas las cuentas que se han proporcionado excepto aquellas que no están activas.
	 * 
	 */
	public Path generarReporteSemanalAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException;

	List<Segregada> devolverInformeHolandaProductoTodasSinIBAN() throws CuentaNoExistenteException;

	List<PersonaAutorizada> devolverInformeHolandaAutorizados(String nombre, String apellidos, Date fechaAlta,
			Date fechaBaja);

}
