package es.uma.proyecto.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

@Stateless
public class InformeEJB implements GestionInforme {

	private static final Logger LOG = Logger.getLogger(InformeEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public List<Segregada> devolverInformeHolandaProductoTodas(String IBAN) throws CuentaNoExistenteException {
		List<Segregada> listadoCuentas = new ArrayList<>();
		Segregada seg = em.find(Segregada.class, IBAN);
		if (seg == null) {
			throw new CuentaNoExistenteException();
		}
		listadoCuentas.add(seg);
		return listadoCuentas;
	}
	
	@Override
	public List<Segregada> devolverInformeHolandaProductoTodasSinIBAN() throws CuentaNoExistenteException {
		List<Segregada> listadoCuentas = new ArrayList<>();
		TypedQuery<Segregada> query = em.createQuery("SELECT c FROM Segregada c", Segregada.class);
		listadoCuentas= query.getResultList();
		if (listadoCuentas.size() == 0) {
			throw new CuentaNoExistenteException();
		}
		
		return listadoCuentas;
	}

	@Override
	public List<Segregada> devolverInformeHolandaProductoInactivas(String IBAN) throws CuentaNoExistenteException {

		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado AND s.iban = :iban");
		query.setParameter("estado", "baja");
		query.setParameter("iban", IBAN);

		List<Segregada> listadoCuentas = query.getResultList();

		if (listadoCuentas.size() == 0) {
			throw new CuentaNoExistenteException();
		}

		return listadoCuentas;

	}

	@Override
	public List<Segregada> devolverInformeHolandaProductoActivas(String IBAN) throws CuentaNoExistenteException {

		Query query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado AND s.iban = :iban");
		query.setParameter("estado", "activa");
		query.setParameter("iban", IBAN);

		List<Segregada> listadoCuentas = query.getResultList();

		if (listadoCuentas.size() == 0) {
			throw new CuentaNoExistenteException();
		}

		return listadoCuentas;

	}

	@Override
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta,
			Date fechaBaja)  {
		Query query = em.createQuery(
				"SELECT i FROM Individual i where i.nombre = :nombre AND i.apellido = :apellido AND i.fechaAlta = :fechaalta"
						+ " AND i.fechaBaja = :fechabaja");

		query.setParameter("nombre", nombre);
		query.setParameter("apellido", apellidos);
		query.setParameter("fechaalta", fechaAlta);
		query.setParameter("fechabaja", fechaBaja);
		

		List<Individual> listaClientes = query.getResultList();
		

		return listaClientes;
	}

	
	@Override
	public List<PersonaAutorizada> devolverInformeHolandaAutorizados(String nombre, String apellidos, Date fechaAlta,
			Date fechaBaja)  {
		Query query = em.createQuery(
				"SELECT i FROM PersonaAutorizada i where i.nombre = :nombre AND i.apellidos = :apellido AND i.fechainicio = :fechaalta"
						+ " AND i.fechafin = :fechabaja");

		query.setParameter("nombre", nombre);
		query.setParameter("apellido", apellidos);
		query.setParameter("fechaalta", fechaAlta);
		query.setParameter("fechabaja", fechaBaja);
		

		List<PersonaAutorizada> listaAutorizados = query.getResultList();
		

		return listaAutorizados;
	}
	public String generarReporteInicialAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		String nombre_archivo_csv;

		TypedQuery<Segregada> query = em.createQuery("SELECT c FROM Segregada c", Segregada.class);
		List<Segregada> cuentas = query.getResultList();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		Random rnd = new Random();
	    int numero = rnd.nextInt(9999);
		
		nombre_archivo_csv = new String("FINTECH_IBAN_1_" + numero + ".csv");
		String ruta = new String("C:\\Users\\PC\\Documents\\GitHub\\PILOTECH\\proyecto\\proyecto-war\\target\\generated-sources\\" + nombre_archivo_csv);
		
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(ruta));
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			csvPrinter.print("Ebury_IBAN_" + dtf2.format(LocalDateTime.now()).trim() + "\n");
			for (CuentaFintech c : cuentas) {

				Empresa clienteEmpresa = em.find(Empresa.class, c.getCliente().getIdentificacion());

				if (clienteEmpresa == null) {

					Individual clienteIndividual = em.find(Individual.class, c.getCliente().getIdentificacion());

					String fecha_nacimiento;

					if (clienteIndividual.getFechaNacimiento() == null) {

						fecha_nacimiento = new String("noexistente");
					} else {

						fecha_nacimiento = clienteIndividual.getFechaNacimiento().toString();
					}

					csvPrinter.printRecord(c.getIban(), clienteIndividual.getApellido(), clienteIndividual.getNombre(),
							clienteIndividual.getDireccion(), clienteIndividual.getCiudad(),
							clienteIndividual.getCodigopostal(), clienteIndividual.getPais(),
							clienteIndividual.getIdentificacion(), fecha_nacimiento);

				} else {

					for (Autorizacion a : clienteEmpresa.getAutorizacions()) {

						String fecha_nacimiento;

						if (a.getPersonaAutorizada().getEstado().equals("activo")) {

							if (a.getPersonaAutorizada().getFechaNacimiento() == null) {

								fecha_nacimiento = new String("noexistente");
							} else {

								fecha_nacimiento = a.getPersonaAutorizada().getFechaNacimiento().toString();
							}

							csvPrinter.printRecord(c.getIban(), a.getPersonaAutorizada().getApellidos(),
									a.getPersonaAutorizada().getNombre(), a.getPersonaAutorizada().getDireccion(),
									clienteEmpresa.getCiudad(), clienteEmpresa.getCodigopostal(),
									clienteEmpresa.getPais(), clienteEmpresa.getIdentificacion(), fecha_nacimiento);
						}
					}
				}
			}

			csvPrinter.flush();
			csvPrinter.close();

		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		return nombre_archivo_csv;
	}

	public String generarReporteSemanalAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		String nombre_archivo_csv;

		TypedQuery<Segregada> query = em.createQuery("SELECT s FROM Segregada s where s.estado = :estado", Segregada.class);
		query.setParameter("estado", "activa");
		List<Segregada> cuentas = query.getResultList();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = dtf.format(LocalDateTime.now());
		
		Random rnd = new Random();
	    int numero = rnd.nextInt(9999);
		
		nombre_archivo_csv = "FINTECH_IBAN_2_"+ numero + ".csv";
		String ruta = new String("C:\\Users\\PC\\Documents\\GitHub\\PILOTECH\\proyecto\\proyecto-war\\target\\generated-sources\\" + nombre_archivo_csv);
		
		
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(ruta));
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			for (CuentaFintech c : cuentas) {


					Empresa clienteEmpresa = em.find(Empresa.class, c.getCliente().getIdentificacion());

					if (clienteEmpresa == null) {

						Individual clienteIndividual = em.find(Individual.class, c.getCliente().getIdentificacion());

						String fecha_nacimiento;

						if (clienteIndividual.getFechaNacimiento() == null) {

							fecha_nacimiento = new String("noexistente");
						} else {

							fecha_nacimiento = clienteIndividual.getFechaNacimiento().toString();
						}

						csvPrinter.printRecord(c.getIban(), clienteIndividual.getApellido(),
								clienteIndividual.getNombre(), clienteIndividual.getDireccion(),
								clienteIndividual.getCiudad(), clienteIndividual.getCodigopostal(),
								clienteIndividual.getPais(), clienteIndividual.getIdentificacion(), fecha_nacimiento);

					} else {

						for (Autorizacion a : clienteEmpresa.getAutorizacions()) {

							String fecha_nacimiento;

							if (a.getPersonaAutorizada().getEstado().equals("activo")) {

								if (a.getPersonaAutorizada().getFechaNacimiento() == null) {

									fecha_nacimiento = new String("noexistente");
								} else {

									fecha_nacimiento = a.getPersonaAutorizada().getFechaNacimiento().toString();
								}

								csvPrinter.printRecord(c.getIban(), a.getPersonaAutorizada().getApellidos(),
										a.getPersonaAutorizada().getNombre(), a.getPersonaAutorizada().getDireccion(),
										clienteEmpresa.getCiudad(), clienteEmpresa.getCodigopostal(),
										clienteEmpresa.getPais(), clienteEmpresa.getIdentificacion(), fecha_nacimiento);
							}
						}
					}
			}

			csvPrinter.flush();
			csvPrinter.close();

		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		return nombre_archivo_csv;

	}

}
