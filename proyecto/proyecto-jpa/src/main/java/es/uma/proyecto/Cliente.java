package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String ciudad;

	private BigDecimal codigopostal;

	private String direccion;

	@Column(name="FECHA_ALTA")
	private Object fechaAlta;

	@Column(name="FECHA_BAJA")
	private Object fechaBaja;

	private String identificacion;

	private String pais;

	@Column(name="TIPO_CLIENTE")
	private String tipoCliente;

	//bi-directional many-to-one association to CuentaFintech
	@OneToMany(mappedBy="cliente")
	private List<CuentaFintech> cuentaFinteches;

	//bi-directional one-to-one association to Empresa
	@OneToOne(mappedBy="cliente")
	private Empresa empresa;

	//bi-directional one-to-one association to Individual
	@OneToOne(mappedBy="cliente")
	private Individual individual;

	public Cliente() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public BigDecimal getCodigopostal() {
		return this.codigopostal;
	}

	public void setCodigopostal(BigDecimal codigopostal) {
		this.codigopostal = codigopostal;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Object getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Object fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Object getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Object fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTipoCliente() {
		return this.tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public List<CuentaFintech> getCuentaFinteches() {
		return this.cuentaFinteches;
	}

	public void setCuentaFinteches(List<CuentaFintech> cuentaFinteches) {
		this.cuentaFinteches = cuentaFinteches;
	}

	public CuentaFintech addCuentaFintech(CuentaFintech cuentaFintech) {
		getCuentaFinteches().add(cuentaFintech);
		cuentaFintech.setCliente(this);

		return cuentaFintech;
	}

	public CuentaFintech removeCuentaFintech(CuentaFintech cuentaFintech) {
		getCuentaFinteches().remove(cuentaFintech);
		cuentaFintech.setCliente(null);

		return cuentaFintech;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Individual getIndividual() {
		return this.individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

}