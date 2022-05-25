package es.uma.proyecto.rest;

import java.net.URI;

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

import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.modelsrest.PeticionClientes;



@Path("")
public class ServicioREST {
	@EJB
	private GestionInforme negocio;
	
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
	
	/*
	@Path("/clients")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response aniadirContacto(PeticionClientes pc) {
		
		Usuario usuario  = getUsuario();
		if(usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			contacto.setUsuario(usuario);
			negocio.insertar(contacto);
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
	*/
}
