package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	*/
	
	@Id
	/*@Column(nullable = false, unique = true)*/
	private String identificacion;
	
	@Column(name="TIPO_CLIENTE", nullable = false)
	private String tipoCliente;
	
	@Column(nullable = false)
	private String estado;
	
	@Column(name="FECHA_ALTA", nullable = false)
	private Date fechaAlta;

	@Column(name="FECHA_BAJA")
	private Date fechaBaja;
	
	@Column(nullable = false)
	private String direccion;
	
	@Column(nullable = false)
	private String ciudad;

	@Column(nullable = false)
	private Integer codigopostal;

	@Column(nullable = false)
	private String pais;

	//bi-directional many-to-one association to CuentaFintech
	@OneToMany(mappedBy="cliente")
	private List<CuentaFintech> cuentaFinteches;
	
	@OneToOne(mappedBy = "cliente")
	@JoinColumn(name = "Usuario")
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	public Cliente() {
	}

	/*
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	 */
	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getCodigopostal() {
		return this.codigopostal;
	}

	public void setCodigopostal(Integer codigopostal) {
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

	/*public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}*/
	
	public void setFechaAlta(String fecha) {
			this.fechaAlta = Date.valueOf(fecha);
	}

	public Object getFechaBaja() {
		return this.fechaBaja;
	}

	/*public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}*/
	
	public void setFechaBaja(String fecha) {
		if(!fecha.equals(null)) {
			this.fechaBaja = Date.valueOf(fecha);
		} else {
			this.fechaBaja = Date.valueOf("0000-00-00");;
		}
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
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	@Override
	public int hashCode() {
		return Objects.hash(identificacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(identificacion, other.identificacion);
	}

	@Override
	public String toString() {
		return "Cliente [identificacion=" + identificacion + "]";
	}
	
	
	
}