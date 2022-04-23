package es.uma.proyecto.ejb.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
