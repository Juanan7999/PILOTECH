package es.uma.proyecto.ejb;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InformeAlemaniaEJB implements GestionInformeAlemania {

	private static final Logger LOG = Logger.getLogger(CuentaEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
	
	
	
	
}
