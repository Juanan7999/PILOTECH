package es.uma.proyecto.rest;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.modelsrest.AccountHolder;
import es.uma.proyecto.modelsrest.DireccionCliente;
import es.uma.proyecto.modelsrest.NombreCliente;
import es.uma.proyecto.modelsrest.PeticionClientes;
import es.uma.proyecto.modelsrest.PeticionProductos;
import es.uma.proyecto.modelsrest.Producto;
import es.uma.proyecto.modelsrest.RespuestaProductos;



@Path("")
public class ServicioREST {
	@EJB
	private GestionInforme informe;
	
	@EJB
	private GestionCliente cliente;
	
	@Context
	private UriInfo uriInfo;
	
	@HeaderParam("User-auth")
	private String autorizacion;
	
	@Path("/healthcheck")
	@GET
	public Response getHealthcheck() {
		
			return Response.ok().build();
		
	}
	

	@Path("/clients")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response peticionClientes(PeticionClientes pc) {
		
		Usuario usuario  = getUsuario();
		if(usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			informe.
			contacto.setUsuario(usuario);
			negocio.insertar(contacto);
			URI uriContacto = uriInfo.getBaseUriBuilder().path("api").path("agenda").path("contacto").path(contacto.getId().toString()).build();
			return Response.created(uriContacto).status(Status.CREATED).build();
			
		}catch(AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/products")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response peticionProductos(PeticionProductos pp) {
		List<Segregada> cuentas;
		if(pp.getProductNumber() != null) {
		
		if(pp.getStatus().equals("active")) {
			cuentas = informe.devolverInformeHolandaProductoActivas(pp.getProductNumber());
		}else if(pp.getStatus().equals("inactive")) {
			cuentas = informe.devolverInformeHolandaProductoInactivas(pp.getProductNumber());
		}else {
			cuentas = informe.devolverInformeHolandaProductoTodas(pp.getProductNumber());
		}
		
		}else {
			cuentas = informe.devolverInformeHolandaProductoTodasSinIBAN();
		}
		
		
		RespuestaProductos rp = new RespuestaProductos();
		
		for(Segregada s : cuentas) {
			Producto p = new Producto();
			p.setProductNumber(s.getIban());
			p.setStartDate(s.getFechaApertura().toString());
			if(p.getEndDate() == null) {
				p.setEndDate("non-existent");
			}else {
				p.setEndDate(s.getFechaCierre().toString());
			}
			
			
			AccountHolder ah = new AccountHolder();
			Cliente cl = s.getCliente();
			if(cl.getTipoCliente().equals("N")) {
				ah.setAccounttype("Fisica");
				ah.setActiveCostumer(s.getEstado().equals("activa"));
				Individual ind = (Individual) cliente.devolverCliente(cl.getIdentificacion());
				NombreCliente nc = new NombreCliente();
				nc.setFirstName(ind.getNombre());
				nc.setLastName(ind.getApellido());
				ah.setName(nc);
				
			}else {
				ah.setAccounttype("Empresa");
				ah.setActiveCostumer(s.getEstado().equals("activa"));
				Empresa emp = (Empresa) cliente.devolverClienteEmpresa(cl.getIdentificacion());
				NombreCliente nc = new NombreCliente();
				nc.setFirstName(emp.getRazonSocial());  //REVISAR!!!!!!!!!!!!
				
				
				ah.setName(nc);
			}
			
				DireccionCliente direccion = new DireccionCliente();
				direccion.setCity(cl.getCiudad());
				direccion.setCountry(cl.getPais());
				direccion.setStreetNumber(cl.getDireccion());
				direccion.setPostalCode(cl.getCodigopostal().toString());
			
			
		}
		
		
		try {
			
			
			URI uriContacto = uriInfo.getBaseUriBuilder().path("api").path("agenda").path("contacto").path(contacto.getId().toString()).build();
			return Response.created(uriContacto).status(Status.CREATED).build();
			
		}catch(AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	
	@Path("/contacto/{id}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response devuelveContacto(@PathParam("id") Long id) {
		Usuario usuario  = getUsuario();
		if(usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			Contacto contacto = negocio.obtenerContacto(usuario, id);
			return Response.ok(contacto).build();
			
		}catch(AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/contacto/{id}")
	@DELETE
	public Response eliminarContacto(@PathParam("id") Long id) {
		Usuario usuario  = getUsuario();
		if(usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			Contacto contacto = negocio.obtenerContacto(usuario, id);
			negocio.eliminarContacto(contacto);
			return Response.ok().build();
			
		}catch(AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@Path("/contacto/{id}")
	@PUT
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response modificarContacto(@PathParam("id") Long id, Contacto c) {
		Usuario usuario = getUsuario();
		if(usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			c.setId(id);
			c.setUsuario(usuario);
			negocio.modificar(c);
			return Response.ok().build();
		} catch(AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	private Usuario getUsuario() {
		if (autorizacion == null) {
			return null;
		}
		
		String [] partesAutorizacion = autorizacion.split(":");
		if (partesAutorizacion.length != 2) {
			return null;
		}
		
		Usuario usuario = new Usuario();
		usuario.setCuenta(partesAutorizacion[0]);
		usuario.setContrasenia(partesAutorizacion[1]);
		
		return usuario;
	}
	
}
