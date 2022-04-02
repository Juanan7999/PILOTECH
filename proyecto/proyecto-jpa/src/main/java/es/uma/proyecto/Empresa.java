package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the EMPRESA database table.
 * 
 */
@Entity
@DiscriminatorValue("empresa")
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e")
public class Empresa  extends Cliente implements Serializable{
	private static final long serialVersionUID = 1L;

	//@Id
	//private String id;
	
	@Column(name="RAZON_SOCIAL")
	private String razonSocial;

	//bi-directional many-to-one association to Autorizacion
	@OneToMany(mappedBy="empresa")
	private List<Autorizacion> autorizacions;

	public Empresa() {
	}

	/*
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	*/
	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public List<Autorizacion> getAutorizacions() {
		return this.autorizacions;
	}

	public void setAutorizacions(List<Autorizacion> autorizacions) {
		this.autorizacions = autorizacions;
	}

	public Autorizacion addAutorizacion(Autorizacion autorizacion) {
		getAutorizacions().add(autorizacion);
		autorizacion.setEmpresa(this);

		return autorizacion;
	}

	public Autorizacion removeAutorizacion(Autorizacion autorizacion) {
		getAutorizacions().remove(autorizacion);
		autorizacion.setEmpresa(null);

		return autorizacion;
	}
}