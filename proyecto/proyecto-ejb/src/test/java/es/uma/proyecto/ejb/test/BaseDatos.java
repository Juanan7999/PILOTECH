package es.uma.proyecto.ejb.test;

import java.math.BigDecimal;

import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;

/*
import es.uma.informatica.sii.ejb.practica.entidades.Ingrediente;
import es.uma.informatica.sii.ejb.practica.entidades.Lote;
import es.uma.informatica.sii.ejb.practica.entidades.Producto;
*/

public class BaseDatos {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Usuario usuario_administrativo = new Usuario();
		usuario_administrativo.setNombreUsuario("Juan");
		usuario_administrativo.setPassword("8234");
		usuario_administrativo.setTipo("A");
		
		em.persist(usuario_administrativo);
		
		Individual nuevo_clientebaja = new Individual();
		nuevo_clientebaja.setIdentificacion("77670010");
		nuevo_clientebaja.setTipoCliente("F");
		nuevo_clientebaja.setEstado("baja");
		nuevo_clientebaja.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_clientebaja.setFechaBaja(null);
		
		
		nuevo_clientebaja.setDireccion("Calle Chozuelas");
		nuevo_clientebaja.setCiudad("Alora");
		nuevo_clientebaja.setCodigopostal(29500);
		nuevo_clientebaja.setPais("España");
		
		nuevo_clientebaja.setNombre("Pepe");
		nuevo_clientebaja.setApellido("Acedo");
		nuevo_clientebaja.setFechaNacimiento(null);
		em.persist(nuevo_clientebaja);
		
		Individual nuevo_cliente = new Individual();
		nuevo_cliente.setIdentificacion("77670018");
		nuevo_cliente.setTipoCliente("F");
		nuevo_cliente.setEstado("activo");
		nuevo_cliente.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_cliente.setFechaBaja(null);
		
		
		nuevo_cliente.setDireccion("Calle Chozuelas");
		nuevo_cliente.setCiudad("Alora");
		nuevo_cliente.setCodigopostal(29500);
		nuevo_cliente.setPais("España");
		
		nuevo_cliente.setNombre("Jose");
		nuevo_cliente.setApellido("Garcia");
		nuevo_cliente.setFechaNacimiento(null);
		em.persist(nuevo_cliente);
		
		
		Empresa nueva_empresa = new Empresa();
		nueva_empresa.setIdentificacion("8888");
		nueva_empresa.setTipoCliente("J");
		nueva_empresa.setEstado("activo");
		nueva_empresa.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
			nueva_empresa.setFechaBaja(null);
		
		
		nueva_empresa.setDireccion("Boulevard Pasteur");
		nueva_empresa.setCiudad("Malaga");
		nueva_empresa.setCodigopostal(29010);
		nueva_empresa.setPais("España");
		
		nueva_empresa.setRazonSocial("Pilotech");
		
		
		
		
		
		em.persist(nueva_empresa);
		
		
		Individual nuevo_clientebloqueado = new Individual();
		nuevo_clientebloqueado.setIdentificacion("77670011");
		nuevo_clientebloqueado.setTipoCliente("F");
		nuevo_clientebloqueado.setEstado("bloqueado");
		nuevo_clientebloqueado.setFechaAlta(Date.valueOf("2022-04-23"));
		
		
		nuevo_clientebloqueado.setFechaBaja(null);
		
		
		nuevo_clientebloqueado.setDireccion("Calle Chozuelas");
		nuevo_clientebloqueado.setCiudad("Alora");
		nuevo_clientebloqueado.setCodigopostal(29500);
		nuevo_clientebloqueado.setPais("España");
		
		nuevo_clientebloqueado.setNombre("Ernesto");
		nuevo_clientebloqueado.setApellido("Bloquez");
		nuevo_clientebloqueado.setFechaNacimiento(null);
		em.persist(nuevo_clientebloqueado);
		
		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");
		em.persist(euro);
		
		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);
		
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
