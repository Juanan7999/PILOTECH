package es.uma.proyecto.ejb;

import java.sql.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.AutorizacionPK;
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

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="proyecto-ejb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		
		/*PersonaAutorizada comprobacion = em.find(PersonaAutorizada.class, "Y4001267V");
		
		if(comprobacion != null) {
			return;
		}*/
		Individual individual = new Individual();
		individual.setIdentificacion("63937528N");
		individual.setTipoCliente("F");
		individual.setEstado("activo");
		individual.setFechaAlta("2019-04-23");
		individual.setDireccion("Av. Andalucia");
		individual.setCiudad("Cordoba");
		individual.setCodigopostal(29300);
		individual.setPais("España");
		individual.setNombre("Francisco");
		individual.setApellido("Lopez Campos");
		individual.setFechaNacimiento("1990-05-17");
		
		Usuario juan = new Usuario();
		juan.setNombreUsuario("juan");
		juan.setPassword("juan");
		juan.setTipo("N");
		juan.setCliente(individual);
		individual.setUsuario(juan);

		em.merge(juan);
		em.merge(individual);
		
		Empresa empresa = new Empresa();
		empresa.setIdentificacion("P3310693A");
		empresa.setRazonSocial("Hermanos Lopez");
		empresa.setTipoCliente("J");
		empresa.setEstado("activo");
		empresa.setFechaAlta("2022-04-23");
		empresa.setDireccion("Av. Luis Pastor");
		empresa.setCiudad("Malaga");
		empresa.setCodigopostal(29300);
		empresa.setPais("España");
		
		em.merge(empresa);
		
		
		
		PersonaAutorizada pa = new PersonaAutorizada();
		pa.setIdentificacion("Y4001267V");
		pa.setNombre("Antonio");
		pa.setApellidos("Perez");
		pa.setDireccion("Calle Sevilla");
		pa.setFechaNacimiento("1989-11-07");
		pa.setFechaInicio("2021-07-27");
		pa.setEstado("activo");
		
		em.merge(pa);
		
		
		
		Usuario ana = new Usuario();
		ana.setNombreUsuario("ana");
		ana.setPassword("ana");
		ana.setTipo("N");
		ana.setPersonaAutorizada(pa);
		
		em.merge(ana);
		
		Usuario ponciano = new Usuario();
		ponciano.setNombreUsuario("ponciano");
		ponciano.setPassword("ponciano");
		ponciano.setTipo("A");

		em.merge(ponciano);
		
		Divisa euro = new Divisa();
		euro.setAbreviatura("euro");
		euro.setNombre("euro");
		euro.setCambioeuro(1.0);
		euro.setSimbolo("€");
		em.merge(euro);

		Divisa dolar = new Divisa();
		dolar.setAbreviatura("dolar");
		dolar.setNombre("dolares");
		dolar.setCambioeuro(0.95);
		dolar.setSimbolo("$");
		em.merge(dolar);
		
		Divisa libra = new Divisa();
		libra.setAbreviatura("libra");
		libra.setNombre("libra");
		libra.setCambioeuro(1.17);
		libra.setSimbolo("£");
		em.merge(libra);
		
		
		
		
		Autorizacion aut = new Autorizacion();
		AutorizacionPK id = new AutorizacionPK();
		id.setPersonaAutorizadaId("Y4001267V");
		id.setEmpresaId("P3310693A");
		aut.setId(id);
		aut.setEmpresa(empresa);
		aut.setPersonaAutorizada(pa);
		aut.setTipo(1);
		
		em.merge(aut);
		

		CuentaReferencia cf = new CuentaReferencia();
		
		
		cf.setIban("ES89 1234 1234 1234 1234");
		cf.setFechaApertura("2021-05-24");
		cf.setEstado("activa");
		cf.setNombrebanco("Sabadell");
		cf.setPais("España");
		cf.setSaldo(100.0);
		cf.setSwift("RGWF5785");
		cf.setDivisa(euro);
		cf.setSucursal("12345");
		
		em.merge(cf);

		
		
		
		
		CuentaReferencia cuentaref2 = new CuentaReferencia();
		cuentaref2.setIban("VG57DDVS5173214964983931");
		cuentaref2.setSwift("2345");
		cuentaref2.setNombrebanco("Santander");
		cuentaref2.setSucursal("Plaza mayor");
		cuentaref2.setSaldo(45.0);
		cuentaref2.setFechaApertura("2022-04-25");
		cuentaref2.setEstado("activa");
		cuentaref2.setDivisa(dolar);
		
		em.merge(cuentaref2);
		
		CuentaReferencia cuentaref3 = new CuentaReferencia();
		cuentaref3.setIban("HN47QUXH11325678769785549996");
		cuentaref3.setSwift("2345");
		cuentaref3.setNombrebanco("Santander");
		cuentaref3.setSucursal("Plaza mayor");
		cuentaref3.setSaldo(45.0);
		cuentaref3.setFechaApertura("2022-04-25");
		cuentaref3.setEstado("activa");
		cuentaref3.setDivisa(dolar);
		
		em.merge(cuentaref3);
		
		CuentaReferencia cuentaref4 = new CuentaReferencia();
		cuentaref4.setIban("ES7121007487367264321882");
		cuentaref4.setSwift("2345");
		cuentaref4.setNombrebanco("Santander");
		cuentaref4.setSucursal("Plaza mayor");
		cuentaref4.setSaldo(100.0);
		cuentaref4.setFechaApertura("2022-04-25");
		cuentaref4.setEstado("activa");
		cuentaref4.setDivisa(euro);
		
		em.merge(cuentaref4);
		
		
		CuentaReferencia cuentaref5 = new CuentaReferencia();
		cuentaref5.setIban("VG88HBIJ4257959912673134");
		cuentaref5.setSwift("2345");
		cuentaref5.setNombrebanco("Santander");
		cuentaref5.setSucursal("Plaza mayor");
		cuentaref5.setSaldo(200.0);
		cuentaref5.setFechaApertura("2022-04-25");
		cuentaref5.setEstado("activa");
		cuentaref5.setDivisa(dolar);
		
		em.merge(cuentaref5);
		
		
		CuentaReferencia cuentaref6 = new CuentaReferencia();
		cuentaref6.setIban("GB79BARC20040134265953");
		cuentaref6.setSwift("2345");
		cuentaref6.setNombrebanco("Santander");
		cuentaref6.setSucursal("Plaza mayor");
		cuentaref6.setSaldo(134.0);
		cuentaref6.setFechaApertura("2022-04-25");
		cuentaref6.setEstado("activa");
		cuentaref6.setDivisa(libra);
		
		em.merge(cuentaref6);
		
		Segregada segregada = new Segregada();
		segregada.setIban("NL63ABNA6548268733");
		segregada.setSwift("2347");
		segregada.setEstado("activa");
		segregada.setFechaApertura("2022-04-25");
		segregada.setCuentaReferencia(cuentaref2);
		segregada.setCliente(empresa);
		
		em.merge(segregada);
		
		Segregada segregada2 = new Segregada();
		segregada2.setIban("FR5514508000502273293129K55");
		segregada2.setSwift("2347");
		segregada2.setEstado("activa");
		segregada2.setFechaApertura("2022-04-25");
		segregada2.setCuentaReferencia(cuentaref3);
		segregada2.setCliente(empresa);
		
		em.merge(segregada2);
		
		
		Segregada segregada3 = new Segregada();
		segregada3.setIban("DE31500105179261215675");
		segregada3.setSwift("2347");
		segregada3.setEstado("baja");
		segregada3.setFechaApertura("2020-04-25");
		segregada3.setFechaCierre("2021-09-01");
		segregada3.setCuentaReferencia(cuentaref3); //Revisar
		segregada3.setCliente(empresa);
		
		em.merge(segregada3);
		
		PooledAccount pooled = new PooledAccount();
		pooled.setIban("ES8400817251647192321264");
		pooled.setSwift("2346");
		pooled.setEstado("activa");
		pooled.setFechaApertura("2022-04-25");
		pooled.setCliente(individual);
		
		em.merge(pooled);
		
		
		DepositaEn depositaEn1 = new DepositaEn();

		depositaEn1.setSaldo(100.0);
		depositaEn1.setCuentaReferencia(cuentaref4);
		depositaEn1.setPooledAccount(pooled);
		
		em.merge(depositaEn1);
		
		
		DepositaEn depositaEn2 = new DepositaEn();

		depositaEn2.setSaldo(200.0);
		depositaEn2.setCuentaReferencia(cuentaref5);
		depositaEn2.setPooledAccount(pooled);
		
		em.merge(depositaEn2);
		
		
		DepositaEn depositaEn3 = new DepositaEn();

		depositaEn3.setSaldo(134.0);
		depositaEn3.setCuentaReferencia(cuentaref6);
		depositaEn3.setPooledAccount(pooled);
		
		em.merge(depositaEn3);
		
		
		Transaccion transaccion = new Transaccion();
		transaccion.setIdUnico(1234);
		transaccion.setCantidad(200.0);
		transaccion.setComision(null);
		transaccion.setCuenta1(segregada);
		transaccion.setCuenta2(pooled);
		transaccion.setDivisa1(dolar);
		transaccion.setDivisa2(dolar);
		transaccion.setFechaejecucion("2022-04-25");
		transaccion.setFechainstruccion("2022-04-25");
		transaccion.setInternacional(null);
		transaccion.setTipo("CD");
		em.merge(transaccion);
		
		
	}
}
