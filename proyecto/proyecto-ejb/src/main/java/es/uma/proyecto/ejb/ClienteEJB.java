package es.uma.proyecto.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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


@Stateless
public class ClienteEJB implements GestionCliente{

	private static final Logger LOG = Logger.getLogger(ClienteEJB.class.getCanonicalName());
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void altaClienteIndividual(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento) throws ClienteExistenteException, UsuarioNoEsAdministrativoException {
		Individual clienteIndividualEntity = em.find(Individual.class, identificacion);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteIndividualEntity != null) {
			throw new ClienteExistenteException();
		}
		
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion(identificacion);
		nuevo_cliente.setTipoCliente(tipo);
		nuevo_cliente.setEstado(estado);
		nuevo_cliente.setFechaAlta(fecha_alta);
		
		if(fecha_baja !=null) {
			nuevo_cliente.setFechaBaja(fecha_baja);
		}
		
		nuevo_cliente.setDireccion(direccion);
		nuevo_cliente.setCiudad(ciudad);
		nuevo_cliente.setCodigopostal(codigo_postal);
		nuevo_cliente.setPais(pais);
		
		nuevo_cliente.setNombre(nombre);
		nuevo_cliente.setApellido(apellidos);
		
		if(fecha_nacimiento != null) {
			nuevo_cliente.setFechaNacimiento(fecha_nacimiento);
		}
		
	}

	@Override
	public void altaClienteEmpresa(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String razon_social) throws ClienteExistenteException, UsuarioNoEsAdministrativoException{
		Empresa clienteEmpresaEntity = em.find(Empresa.class, identificacion);
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		if(clienteEmpresaEntity != null) {
			throw new ClienteExistenteException();
		}
		
		Empresa nuevo_cliente = new Empresa();
		nuevo_cliente.setIdentificacion(identificacion);
		nuevo_cliente.setTipoCliente(tipo);
		nuevo_cliente.setEstado(estado);
		nuevo_cliente.setFechaAlta(fecha_alta);
		
		if(fecha_baja !=null) {
			nuevo_cliente.setFechaBaja(fecha_baja);
		}
		
		nuevo_cliente.setDireccion(direccion);
		nuevo_cliente.setCiudad(ciudad);
		nuevo_cliente.setCodigopostal(codigo_postal);
		nuevo_cliente.setPais(pais);
		
		nuevo_cliente.setRazonSocial(razon_social);
		
		em.persist(nuevo_cliente);
	}

	@Override
	public void bajaCliente(String idAdmin, String identificacionCliente) throws ClienteNoExistenteException, ClienteYaDeBajaException, CuentaAbiertaException, UsuarioNoEsAdministrativoException {
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		Cliente clienteEntity = em.find(Cliente.class, identificacionCliente);
		
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if(clienteEntity.getEstado().equals("baja")) {
			throw new ClienteYaDeBajaException();
		} 
		
		List<CuentaFintech> cuentas = clienteEntity.getCuentaFinteches();
		
		for(CuentaFintech c : cuentas) {
			if(c.getEstado().equals("activa")) {
				throw new CuentaAbiertaException();
			}
		}
		
		clienteEntity.setEstado("baja");
	}

	@Override
	public void activaCliente(String idAdmin, String identificacionCliente) throws ClienteNoExistenteException, ClienteYaActivoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Cliente clienteEntity = em.find(Cliente.class, identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("activo")) {
			throw new ClienteYaActivoException();
		}
		
		clienteEntity.setEstado("activo");
		
	}

	@Override
	public void bloqueaCliente(String idAdmin, String identificacionCliente) throws ClienteNoExistenteException, ClienteBloqueadoException, UsuarioNoEsAdministrativoException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		
		Cliente clienteEntity = em.find(Cliente.class,identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		} else if (clienteEntity.getEstado().endsWith("bloqueado")) {
			throw new ClienteBloqueadoException();
		}
		
		clienteEntity.setEstado("bloqueado");
		
		
	}
	
	@Override
	public void modificarDatosClienteIndividual(String idAdmin, String identificacion, String tipo, String estado, Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais, String nombre, String apellidos, Date fecha_nacimiento, String identificacionCliente) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Individual clienteEntity = em.find(Individual.class,identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		if(identificacion != null) {
			clienteEntity.setIdentificacion(identificacion);
		}
		if(tipo != null) {
			clienteEntity.setTipoCliente(tipo);
		}
		if(estado != null) {
			clienteEntity.setEstado(estado);
		}
		if(fecha_alta != null) {
			clienteEntity.setFechaAlta(fecha_alta);
		}
		if(fecha_baja != null) {
			clienteEntity.setFechaBaja(fecha_baja);
		}
		if(direccion != null) {
			clienteEntity.setDireccion(direccion);
		}
		if(ciudad != null) {
			clienteEntity.setCiudad(ciudad);
		}
		
		if(codigo_postal != null) {
			clienteEntity.setCodigopostal(codigo_postal);
		}
		
		if(pais != null) {
			clienteEntity.setPais(pais);
		}
		
		if(nombre != null) {
			clienteEntity.setNombre(nombre);
		}
		
		if(apellidos != null) {
			clienteEntity.setApellido(apellidos);
		}
		
		if(fecha_nacimiento != null) {
			clienteEntity.setFechaNacimiento(fecha_nacimiento);
		}
	}
	
	@Override
	public void modificarDatosClienteEmpresa(String idAdmin, String identificacion, String tipo, String estado,
			Date fecha_alta, Date fecha_baja, String direccion, String ciudad, Integer codigo_postal, String pais,
			String razon_social, String identificacionCliente) throws UsuarioNoEsAdministrativoException, ClienteNoExistenteException {
		
		Usuario administrador = em.find(Usuario.class, idAdmin);
		
		if(administrador==null || !administrador.getTipo().equals("A")) { //Si no existe o no es administrativo
			throw new UsuarioNoEsAdministrativoException();
		}
		
		Empresa clienteEntity = em.find(Empresa.class,identificacionCliente);
		
		if(clienteEntity == null) {
			throw new ClienteNoExistenteException();
		}
		
		if(identificacion != null) {
			clienteEntity.setIdentificacion(identificacion);
		}
		if(tipo != null) {
			clienteEntity.setTipoCliente(tipo);
		}
		if(estado != null) {
			clienteEntity.setEstado(estado);
		}
		if(fecha_alta != null) {
			clienteEntity.setFechaAlta(fecha_alta);
		}
		if(fecha_baja != null) {
			clienteEntity.setFechaBaja(fecha_baja);
		}
		
		if(direccion != null) {
			clienteEntity.setDireccion(direccion);
		}
		
		if(ciudad != null) {
			clienteEntity.setCiudad(ciudad);
		}
		
		if(codigo_postal != null) {
			clienteEntity.setCodigopostal(codigo_postal);
		}
		
		if(pais != null) {
			clienteEntity.setPais(pais);
		}
		
		if(razon_social != null) {
			clienteEntity.setRazonSocial(razon_social);
		}
		
	}

	@Override
	public List<Cliente> devolverTodosClientes(){
		TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		List<Cliente> clientes= query.getResultList();
		return clientes;
	}
	
}
