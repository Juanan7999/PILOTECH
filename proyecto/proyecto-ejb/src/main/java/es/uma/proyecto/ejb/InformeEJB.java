package es.uma.proyecto.ejb;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class InformeEJB implements GestionInforme{

	private static final Logger LOG = Logger.getLogger(InformeEJB.class.getCanonicalName());

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
}
