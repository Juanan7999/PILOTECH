package es.uma.proyecto.ejb.test;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;

import es.uma.proyecto.Divisa;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;

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

		Usuario usuario_normal = new Usuario();
		usuario_normal.setNombreUsuario("Jose");
		usuario_normal.setPassword("8234");
		usuario_normal.setTipo("N");
		em.persist(usuario_normal);

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

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(Date.valueOf("2022-04-26"));
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		em.persist(nuevo_cliente2);

		Individual nuevo_cliente3 = new Individual();
		nuevo_cliente3.setIdentificacion("77670002");
		nuevo_cliente3.setTipoCliente("F");
		nuevo_cliente3.setEstado("activo");
		nuevo_cliente3.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente3.setFechaBaja(null);
		nuevo_cliente3.setDireccion("Calle Chozuelas");
		nuevo_cliente3.setCiudad("Alora");
		nuevo_cliente3.setCodigopostal(29500);
		nuevo_cliente3.setPais("España");
		nuevo_cliente3.setNombre("Lucas");
		nuevo_cliente3.setApellido("Garcia");
		nuevo_cliente3.setFechaNacimiento(null);
		em.persist(nuevo_cliente3);

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

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("dolar");
		dolar.setNombre("dolar");
		dolar.setCambioeuro(0.95);
		dolar.setSimbolo("$");
		em.persist(dolar);

		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);
		em.persist(cuentaref);

		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		cuentaref2.setDivisa(dolar);
		em.persist(cuentaref2);

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES1112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);
		em.persist(pooled);

		PooledAccount pooled2 = new PooledAccount();
		pooled2.setIban("ES1115");
		pooled2.setSwift("2346");
		pooled2.setEstado("activa");
		pooled2.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled2.setCliente(nuevo_cliente2);
		em.persist(pooled2);

		Segregada segregada = new Segregada();
		segregada.setIban("ES1113");
		segregada.setSwift("2347");
		segregada.setEstado("activa");
		segregada.setFechaApertura(Date.valueOf("2022-04-25"));
		segregada.setCuentaReferencia(cuentaref);
		segregada.setCliente(nuevo_cliente3);

		Segregada segregada1 = new Segregada();
		segregada1.setIban("ES2022");
		segregada1.setSwift("6789");
		segregada1.setEstado("baja");
		segregada1.setFechaApertura(Date.valueOf("2022-04-28"));
		segregada1.setCuentaReferencia(cuentaref);
		segregada1.setCliente(nuevo_cliente3);
		em.persist(segregada1);

		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(29.0);
		depositaEn1.setCuentaReferencia(cuentaref);
		depositaEn1.setPooledAccount(pooled);
		em.persist(segregada);
		em.persist(depositaEn1);

		PersonaAutorizada personaautorizada = new PersonaAutorizada();
		personaautorizada.setIdentificacion("77670001");
		personaautorizada.setNombre("Oussama");
		personaautorizada.setApellidos("Boutoil");
		personaautorizada.setDireccion("Almargen");
		personaautorizada.setEstado("activo");
		// personaautorizada.setUsuario(usuario_personaAutorizada);

		Usuario usuario_personaAutorizada = new Usuario();
		usuario_personaAutorizada.setNombreUsuario("Oussama");
		usuario_personaAutorizada.setPassword("8234");
		usuario_personaAutorizada.setTipo("N");
		usuario_personaAutorizada.setPersonaAutorizada(personaautorizada);
		;
		em.persist(usuario_personaAutorizada);

		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1234);
		transaccion.setCantidad(30.0);
		transaccion.setComision(1.0);
		transaccion.setCuenta1(cuentaref);
		transaccion.setCuenta2(cuentaref2);
		transaccion.setDivisa1(euro);
		transaccion.setDivisa2(dolar);
		transaccion.setFechaejecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechainstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");
		em.persist(transaccion);

		Autorizacion autorizacion = new Autorizacion();
		autorizacion.setEmpresa(nueva_empresa);
		autorizacion.setPersonaAutorizada(personaautorizada);
		autorizacion.setTipo(1);

		em.persist(personaautorizada);
		em.persist(autorizacion);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
