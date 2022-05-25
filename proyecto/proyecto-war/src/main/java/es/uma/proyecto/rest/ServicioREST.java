package es.uma.proyecto.rest;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
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
import es.uma.proyecto.CuentaFintech;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionCuenta;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.ejb.exceptions.ClienteNoExistenteException;
import es.uma.proyecto.ejb.exceptions.PersonaAutorizadaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;
import es.uma.proyecto.modelsrest.AccountHolder;
import es.uma.proyecto.modelsrest.DireccionCliente;
import es.uma.proyecto.modelsrest.Individuales;
import es.uma.proyecto.modelsrest.NombreCliente;
import es.uma.proyecto.modelsrest.PeticionClientes;
import es.uma.proyecto.modelsrest.PeticionProductos;
import es.uma.proyecto.modelsrest.Producto;
import es.uma.proyecto.modelsrest.ProductoCliente;
import es.uma.proyecto.modelsrest.RespuestaIndividual;
import es.uma.proyecto.modelsrest.RespuestaProductos;



@Path("")
public class ServicioREST {
	@EJB
	private GestionInforme informe;
	
	@EJB
	private GestionCliente cliente;
	
	@EJB
	private GestionCuenta cuenta;
	
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
		
		
		try {
		String fechainicio = pc.getStartPeriod();
		String fechafin = pc.getEndPeriod();
		String nombre = pc.getName().getFirstName();
		String apellidos = pc.getName().getLastName();
		
		
			List<Individual> listaIndividuales = informe.devolverInformeHolandaClientes(nombre, apellidos, Date.valueOf(fechainicio), Date.valueOf(fechafin));
			
			RespuestaIndividual rc = new RespuestaIndividual();
			List<Individuales> personas = new ArrayList<>();
			
			
			if(listaIndividuales.size() != 0) {
				
				for(Individual i : listaIndividuales) {
					Individuales individuales = new Individuales();
					List<Segregada> cuentas = cuenta.devolverSegregadasDeIndividual(i.getIdentificacion());
					List<ProductoCliente> productos = new ArrayList<>();
					for(Segregada cf : cuentas) {
						ProductoCliente prodCl = new ProductoCliente();
						prodCl.setProductNumber(cf.getIban());
						prodCl.setStatus(cf.getEstado());
						prodCl.setStatus("propietaria");
						productos.add(prodCl);
						
						
					}
					
					individuales.setProducts(productos);
					individuales.setActiveCostumer(i.getEstado().equals("activa"));
					individuales.setDateOfBirth(i.getFechaNacimiento().toString());
					NombreCliente name = new NombreCliente();
					name.setFirstName(i.getNombre());
					name.setLastName(i.getApellido());
					individuales.setName(name);
					DireccionCliente direccion = new DireccionCliente();
					direccion.setCity(i.getCiudad());
					direccion.setCountry(i.getPais());
					direccion.setPostalCode(i.getCodigopostal().toString());
					direccion.setStreetNumber(i.getDireccion());
					individuales.setAddress(direccion);
					personas.add(individuales);
				}
			}
			
			List<PersonaAutorizada> listaAutorizados = informe.devolverInformeHolandaAutorizados(nombre, apellidos, Date.valueOf(fechainicio), Date.valueOf(fechafin));
			if(listaAutorizados.size()!=0) {
				for(PersonaAutorizada i : listaAutorizados) {
					Individuales individuales = new Individuales();
					List<Segregada> cuentas = cuenta.devolverSegregadasDeAutorizado(i.getIdentificacion());
					List<ProductoCliente> productos = new ArrayList<>();
					for(Segregada cf : cuentas) {
						ProductoCliente prodCl = new ProductoCliente();
						prodCl.setProductNumber(cf.getIban());
						prodCl.setStatus(cf.getEstado());
						prodCl.setStatus("autorizada");
						productos.add(prodCl);
						
					}
					individuales.setProducts(productos);
					individuales.setActiveCostumer(i.getEstado().equals("activa"));
					individuales.setDateOfBirth(i.getFechaNacimiento().toString());
					NombreCliente name = new NombreCliente();
					name.setFirstName(i.getNombre());
					name.setLastName(i.getApellidos());
					individuales.setName(name);
					DireccionCliente direccion = new DireccionCliente();
					//Estos campos no existen en person autorizada
					direccion.setCity("non-existent");
					direccion.setCountry("non-existent");
					direccion.setPostalCode("non-existent");
					direccion.setStreetNumber(i.getDireccion());
					individuales.setAddress(direccion);
					personas.add(individuales);
				}
			}
			
			
		rc.setIndividual(personas);
		
		
		
		return Response.ok(rc).build();
		
		
		
		
		
		}catch(ProyectoEjbException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	@Path("/products")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response peticionProductos(PeticionProductos pp) {
		
		try {
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
		List<Producto> listaProductos = new ArrayList<>();
		
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
			ah.setAddress(direccion);
			p.setAccountHolder(ah);
			listaProductos.add(p);
		}
		
		
			rp.setProducts(listaProductos);
		
		
			
			
			
			return Response.ok(rp).build();
			
		}catch(ProyectoEjbException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
	}
	
	
}
