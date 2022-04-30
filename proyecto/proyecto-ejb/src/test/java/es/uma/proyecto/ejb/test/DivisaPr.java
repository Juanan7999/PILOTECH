package es.uma.proyecto.ejb.test;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.CuentaReferencia;
import es.uma.proyecto.DepositaEn;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.Individual;

import es.uma.proyecto.PooledAccount;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionDivisa;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.ClientePersonaAutorizadaNoEncontradoException;
import es.uma.proyecto.ejb.exceptions.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.exceptions.CuentasDiferentesException;
import es.uma.proyecto.ejb.exceptions.PooledNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;
import es.uma.proyecto.ejb.exceptions.SaldoInsuficienteException;
import es.uma.proyecto.ejb.exceptions.UsuarioNoEsAdministrativoException;

public class DivisaPr {

	private static final Logger LOG = Logger.getLogger(DivisaPr.class.getCanonicalName());

	private static final String DIVISA_EJB = "java:global/classes/DivisaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String USUARIO_EJB = "java:global/classes/UsuarioEJB";

	private GestionDivisa gestionDivisa;
	private GestionCliente gestionCliente;
	private GestionUsuario gestionUsuario;

	@Before
	public void setup() throws NamingException {
		gestionDivisa = (GestionDivisa) SuiteTest.ctx.lookup(DIVISA_EJB);
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
		gestionUsuario = (GestionUsuario) SuiteTest.ctx.lookup(USUARIO_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Requisitos({ "RF17" })
	@Test
	public void testCambioDivisaClientePersAutorizadaNoEncontrada() {

		try {

			gestionDivisa.cambioDeDivisaCliente_Autorizado("67670001", null, null, null, null, null);
			fail("Debe saltar exception de que el cliente no existe");
		} catch (ClientePersonaAutorizadaNoEncontradoException e) {
			// OK
		} catch (ProyectoEjbException e) {
			fail("Exception inesperada");
		}

	}

	@Requisitos({ "RF17" })
	@Test
	public void testCambioDivisaClientePersAutorizadaPooledNoExistente() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));

		nuevo_cliente2.setFechaBaja(null);

		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");

		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES2112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		try {

			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, null, null, null, null);
			fail("Debe saltar exception de que la pooled account no existe");
		} catch (PooledNoExistenteException e) {
			// OK
		} catch (ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}

	}

	
	
	@Requisitos({ "RF17" })
	@Test
	public void testCambioDivisaClientePersAutorizadaCuentasDiferentes() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(null);
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		
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

		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("euro");
		dolar.setNombre("euro");
		dolar.setCambioeuro(1.0);
		dolar.setSimbolo("€");
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES1112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		PooledAccount pooled2 = new PooledAccount();
		pooled2.setIban("ES1115");
		pooled2.setSwift("2346");
		pooled2.setEstado("activa");
		pooled2.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled2.setCliente(nuevo_cliente3);

		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);
		
		

		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		cuentaref2.setDivisa(dolar);

		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(29.0);
		depositaEn1.setCuentaReferencia(cuentaref);
		depositaEn1.setPooledAccount(pooled);

		DepositaEn depositaEn2 = new DepositaEn();

		depositaEn2.setSaldo(29.0);
		depositaEn2.setCuentaReferencia(cuentaref2);
		depositaEn2.setPooledAccount(pooled2);

		List<DepositaEn> l = new ArrayList<>();
		l.add(depositaEn1);
		l.add(depositaEn2);

		pooled.setDepositaEns(l);
		
		List<DepositaEn> l2 = new ArrayList<>();
		l2.add(depositaEn1);
		cuentaref.setDepositaEns(l2);
		
		List<DepositaEn> l3 = new ArrayList<>();
		l3.add(depositaEn2);
		cuentaref2.setDepositaEns(l3);

		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1235);
		transaccion.setCantidad(30.0);
		transaccion.setComision(1.0);
		transaccion.setCuenta1(cuentaref);
		transaccion.setCuenta2(cuentaref2);
		transaccion.setFechaejecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechainstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");

		try {

			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, cuentaref, cuentaref2, 20.0,transaccion);
			fail("Debe saltar exception de que la pooled account no existe");
		} catch (CuentasDiferentesException e) {
			// OK
		} catch (ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}

	}

	

	
	
	@Requisitos({ "RF17" })
	@Test
	public void testCambioDivisaClientePersAutorizadaSaldoInsuf() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(null);
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		
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

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES1112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		PooledAccount pooled2 = new PooledAccount();
		pooled2.setIban("ES1115");
		pooled2.setSwift("2346");
		pooled2.setEstado("activa");
		pooled2.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled2.setCliente(nuevo_cliente3);

		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("euro");
		dolar.setNombre("euro");
		dolar.setCambioeuro(1.0);
		dolar.setSimbolo("€");

		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);

		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		cuentaref.setDivisa(dolar);

		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(29.0);
		depositaEn1.setCuentaReferencia(cuentaref);
		depositaEn1.setPooledAccount(pooled);

		DepositaEn depositaEn2 = new DepositaEn();

		depositaEn2.setSaldo(29.0);
		depositaEn2.setCuentaReferencia(cuentaref2);
		depositaEn2.setPooledAccount(pooled);

		List<DepositaEn> l = new ArrayList<>();
		l.add(depositaEn1);
		l.add(depositaEn2);

		pooled.setDepositaEns(l);
		
		List<DepositaEn> l2 = new ArrayList<>();
		l2.add(depositaEn1);
		cuentaref.setDepositaEns(l2);
		
		List<DepositaEn> l3 = new ArrayList<>();
		l3.add(depositaEn2);
		cuentaref2.setDepositaEns(l3);

		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1234);
		transaccion.setCantidad(50.0);
		transaccion.setComision(1.0);
		transaccion.setCuenta1(cuentaref);
		transaccion.setCuenta2(cuentaref2);
		transaccion.setFechaejecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechainstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");

		try {

			gestionDivisa.cambioDeDivisaCliente_Autorizado("77670001", pooled, cuentaref, cuentaref2, 50.0,transaccion);
			fail("Debe saltar exception de que el saldo es insuficiente");
		} catch (SaldoInsuficienteException e) {
			// OK
		} catch (ProyectoEjbException e) {
			fail("Exception inesperada");
		}

	}
	
	

	@Requisitos({ "RF18" })
	@Test
	public void testCambioDivisaAdminUsuarioNoEsAdmin() {

		try {
			Usuario admin = gestionUsuario.Login("Jose", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, null, null, null, null, null, null);
			fail("Debe saltar exception de que el usuario no es admin");
		} catch (UsuarioNoEsAdministrativoException e) {
			// OK
		} catch (ContraseñaIncorrectaException e) {
			fail("No deberia saltar excepcion de contraseña incorrecta");
		} catch (ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}

	}

	@Requisitos({ "RF18" })
	@Test
	public void testCambioDivisiaAdminClientePersAutNoEncontrado() {

		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "67670001", null, null, null, null, null);
			fail("Debe saltar exception de que el cliente no existe");
		} catch (ClientePersonaAutorizadaNoEncontradoException e) {
			// OK
		} catch (ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		} catch (ProyectoEjbException e) {
			fail("Exception inesperada");
		}

	}

	@Requisitos({ "RF18" })
	@Test
	public void testCambioDivisaAdminPooledNoExistente() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(null);
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		
		

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES2112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, null, null, null, null);
			fail("Debe saltar exception de que la pooled no existe");
		} catch (PooledNoExistenteException e) {

		} catch (ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		} catch (ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}

	}

	
	
	@Requisitos({ "RF18" })
	@Test
	public void testCambioDivisaAdminCuentasDiferentes() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(null);
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		
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

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES1112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		PooledAccount pooled2 = new PooledAccount();
		pooled2.setIban("ES1115");
		pooled2.setSwift("2346");
		pooled2.setEstado("activa");
		pooled2.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled2.setCliente(nuevo_cliente3);
		
		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("euro");
		dolar.setNombre("euro");
		dolar.setCambioeuro(1.0);
		dolar.setSimbolo("€");

		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);

		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		cuentaref2.setDivisa(dolar);

		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(29.0);
		depositaEn1.setCuentaReferencia(cuentaref);
		depositaEn1.setPooledAccount(pooled);
	

		DepositaEn depositaEn2 = new DepositaEn();

		depositaEn2.setSaldo(29.0);
		depositaEn2.setCuentaReferencia(cuentaref2);
		depositaEn2.setPooledAccount(pooled2);

		List<DepositaEn> l = new ArrayList<>();
		l.add(depositaEn1);
		l.add(depositaEn2);

		pooled.setDepositaEns(l);

		List<DepositaEn> l2 = new ArrayList<>();
		l2.add(depositaEn1);
		cuentaref.setDepositaEns(l2);
		
		List<DepositaEn> l3 = new ArrayList<>();
		l3.add(depositaEn2);
		cuentaref2.setDepositaEns(l3);

		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1235);
		transaccion.setCantidad(30.0);
		transaccion.setComision(1.0);
		transaccion.setCuenta1(cuentaref);
		transaccion.setCuenta2(cuentaref2);
		transaccion.setFechaejecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechainstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");

		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, cuentaref, cuentaref2, 20.0, transaccion);
			fail("Debe saltar exception de que la pooled account no existe");
		} catch (CuentasDiferentesException e) {
			// OK
		} catch (ContraseñaIncorrectaException e) {
			fail("No deberia saltar exception de contraseña incorrecta");
		} catch (ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}

	}
	
	

	
	
	@Requisitos({ "RF18" })
	@Test
	public void testCambioDivisiaAdminSaldoInsuf() {

		Individual nuevo_cliente2 = new Individual();
		nuevo_cliente2.setIdentificacion("77670019");
		nuevo_cliente2.setTipoCliente("F");
		nuevo_cliente2.setEstado("activo");
		nuevo_cliente2.setFechaAlta(Date.valueOf("2022-04-23"));
		nuevo_cliente2.setFechaBaja(null);
		nuevo_cliente2.setDireccion("Calle Chozuelas");
		nuevo_cliente2.setCiudad("Alora");
		nuevo_cliente2.setCodigopostal(29500);
		nuevo_cliente2.setPais("España");
		nuevo_cliente2.setNombre("Alberto");
		nuevo_cliente2.setApellido("Garcia");
		nuevo_cliente2.setFechaNacimiento(null);
		
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

		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES1112");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled.setCliente(nuevo_cliente2);

		PooledAccount pooled2 = new PooledAccount();
		pooled2.setIban("ES1115");
		pooled2.setSwift("2346");
		pooled2.setEstado("activa");
		pooled2.setFechaApertura(Date.valueOf("2022-04-25"));
		pooled2.setCliente(nuevo_cliente3);

		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("euro");
		dolar.setNombre("euro");
		dolar.setCambioeuro(1.0);
		dolar.setSimbolo("€");

		CuentaReferencia cuentaref = new CuentaReferencia();
		cuentaref.setIban("ES1111");
		cuentaref.setSwift("2345");
		cuentaref.setNombrebanco("Santander");
		cuentaref.setSucursal("Plaza mayor");
		cuentaref.setSaldo(45.0);
		cuentaref.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref.setEstado("activa");
		cuentaref.setDivisa(euro);

		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("ES1114");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura(Date.valueOf("2022-04-25"));
		cuentaref2.setEstado("activa");
		cuentaref.setDivisa(dolar);

		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(29.0);
		depositaEn1.setCuentaReferencia(cuentaref);
		depositaEn1.setPooledAccount(pooled);

		DepositaEn depositaEn2 = new DepositaEn();

		depositaEn2.setSaldo(29.0);
		depositaEn2.setCuentaReferencia(cuentaref2);
		depositaEn2.setPooledAccount(pooled);

		List<DepositaEn> l = new ArrayList<>();
		l.add(depositaEn1);
		l.add(depositaEn2);

		pooled.setDepositaEns(l);
		
		List<DepositaEn> l2 = new ArrayList<>();
		l2.add(depositaEn1);
		cuentaref.setDepositaEns(l2);
		
		List<DepositaEn> l3 = new ArrayList<>();
		l3.add(depositaEn2);
		cuentaref2.setDepositaEns(l3);

		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1234);
		transaccion.setCantidad(50.0);
		transaccion.setComision(1.0);
		transaccion.setCuenta1(cuentaref);
		transaccion.setCuenta2(cuentaref2);
		transaccion.setFechaejecucion(Date.valueOf("2022-04-25"));
		transaccion.setFechainstruccion(Date.valueOf("2022-04-25"));
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");

		try {
			Usuario admin = gestionUsuario.Login("Juan", "8234");
			gestionDivisa.cambioDeDivisaAdmin(admin, "77670001", pooled, cuentaref, cuentaref2, 50.0, transaccion);
			fail("Debe saltar exception de que el saldo es insuficiente");
		} catch (SaldoInsuficienteException e) {

		} catch (ContraseñaIncorrectaException e) {
			fail("No deberia saltar excepcion de contraseña incorrecta");
		} catch (ProyectoEjbException e) {
			fail("Exception inesperada");
		}

	}
	
	

}
