package es.uma.proyecto.ejb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.ejb.GestionCliente;
import es.uma.proyecto.ejb.GestionInforme;
import es.uma.proyecto.ejb.GestionUsuario;
import es.uma.proyecto.ejb.exceptions.CuentaNoExistenteException;
import es.uma.proyecto.ejb.exceptions.ProyectoEjbException;

public class InformePr {

	private static final Logger LOG = Logger.getLogger(ClientePr.class.getCanonicalName());

	private static final String INFORME_EJB = "java:global/classes/InformeEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejbTest";
	
	private GestionInforme gestionInforme;
	
	@Before
	public void setup() throws NamingException  {
		gestionInforme = (GestionInforme) SuiteTest.ctx.lookup(INFORME_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Requisitos({"RF11"})
	@Test
	public void testDevolverProductosTodasHolandaExistente() {
		
		try {
			List<Segregada> lista = gestionInforme.devolverInformeHolandaProductoTodas("ES1113");
			assertEquals(lista.size(),1);
		}catch(CuentaNoExistenteException e) {
			fail("La cuenta segregada si existe");
		}catch(ProyectoEjbException e) {
			fail("Excepcion inesperada");
		}
		
	}
	
}
