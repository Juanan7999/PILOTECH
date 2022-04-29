package es.uma.proyecto.ejb;

import java.util.List;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class InformeEJB implements GestionInforme {

	private static final Logger LOG = Logger.getLogger(InformeEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public List<Individual> devolverInformeHolandaClientes(String nombre, String apellidos, Date fechaAlta, Date fechaBaja, String direccion) throws ClienteNoExistenteException{
		Query query = em.createQuery("SELECT i FROM Individual i where s.nombre = :nombre AND s.apellido = :apellido AND s.fechaAlta = :fechaalta"
				+ " AND s.fechaBaja = :fechabaja AND s.direccion = :direccion");
		
		query.setParameter("nombre" , nombre);
		query.setParameter("apellido" , apellidos);
		query.setParameter("fechaalta" , fechaAlta);
		query.setParameter("fechabaja" , fechaBaja);
		query.setParameter("direccion" , direccion);
		
		
		List<Individual> listaClientes = query.getResultList();
		if(listaClientes == null) {
			throw new ClienteNoExistenteException();
		}
		
		return listaClientes;
	}
	
	
	public void generarReporteInicialAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		String nombre_archivo_csv;

		TypedQuery<CuentaFintech> query = em.createQuery("SELECT c FROM CLIENTE c", CuentaFintech.class);
		List<CuentaFintech> customers = query.getResultList();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String date = dtf.format(LocalDateTime.now());
		nombre_archivo_csv = new String("./FINTECH_IBAN_");
		nombre_archivo_csv = nombre_archivo_csv + date + ".csv";

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(nombre_archivo_csv));
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			csvPrinter.print("Ebury_IBAN_" + dtf2.format(LocalDateTime.now()).trim() + "\n");
			for (CuentaFintech c : customers) {

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
	}

	public void generarReporteSemanalAlemania(Usuario usuario) throws UsuarioNoEsAdministrativoException {

		Usuario administrador = em.find(Usuario.class, usuario.getNombreUsuario());

		if (administrador == null || !administrador.getTipo().equals("A")) { // Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}

		String nombre_archivo_csv;

		TypedQuery<CuentaFintech> query = em.createQuery("SELECT c FROM CLIENTE c", CuentaFintech.class);
		List<CuentaFintech> customers = query.getResultList();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = dtf.format(LocalDateTime.now());
		nombre_archivo_csv = new String("./FINTECH_IBAN_");
		nombre_archivo_csv = nombre_archivo_csv + date + ".csv";

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(nombre_archivo_csv));
			@SuppressWarnings("deprecation")
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name",
					"Firs_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth"));
			for (CuentaFintech c : customers) {

				if (c.getEstado().equals("activa")) {

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
			}

			csvPrinter.flush();
			csvPrinter.close();

		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

}
