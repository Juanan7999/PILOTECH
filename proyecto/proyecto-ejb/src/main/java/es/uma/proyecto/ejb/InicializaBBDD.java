package es.uma.proyecto.ejb;

import java.sql.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.AutorizacionPK;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.Individual;
import es.uma.proyecto.PersonaAutorizada;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Usuario;

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="proyecto-ejb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		
		PersonaAutorizada comprobacion = em.find(PersonaAutorizada.class, "Y4001267V");
		
		if(comprobacion != null) {
			return;
		}
		
		Usuario juan = new Usuario();
		juan.setNombreUsuario("juan");
		juan.setPassword("juan");
		juan.setTipo("N");

		em.persist(juan);
		
		Usuario ana = new Usuario();
		ana.setNombreUsuario("ana");
		ana.setPassword("ana");
		ana.setTipo("N");
		
		em.persist(ana);
		
		Usuario ponciano = new Usuario();
		ponciano.setNombreUsuario("ponciano");
		ponciano.setPassword("ponciano");
		ponciano.setTipo("A");

		em.persist(ponciano);
		
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
		
		em.persist(empresa);
		
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
		
		em.persist(individual);
		
		PersonaAutorizada pa = new PersonaAutorizada();
		pa.setIdentificacion("Y4001267V");
		pa.setNombre("Antonio");
		pa.setApellidos("Perez");
		pa.setDireccion("Calle Sevilla");
		pa.setFechaNacimiento("1989-11-07");
		pa.setFechaInicio("2021-07-27");
		pa.setEstado("activo");
		
		em.persist(pa);
		
		
		Autorizacion aut = new Autorizacion();
		AutorizacionPK id = new AutorizacionPK();
		id.setPersonaAutorizadaId("Y4001267V");
		id.setEmpresaId("P3310693A");
		aut.setId(id);
		aut.setEmpresa(empresa);
		aut.setPersonaAutorizada(pa);
		aut.setTipo(1);
		
		em.persist(aut);
		
	}
}
