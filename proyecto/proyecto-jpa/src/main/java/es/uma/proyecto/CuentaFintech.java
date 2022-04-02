package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CUENTA_FINTECH database table.
 * 
 */
@Entity
@Table(name="CUENTA_FINTECH")
@NamedQuery(name="CuentaFintech.findAll", query="SELECT c FROM CuentaFintech c")
public class CuentaFintech implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private String clasificacion;

	private String estado;

	@Column(name="FECHA_APERTURA")
	private Object fechaApertura;

	@Column(name="FECHA_CIERRE")
	private Object fechaCierre;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	private Cliente cliente;

	//bi-directional one-to-one association to Cuenta
	@OneToOne
	@JoinColumn(name="IBAN")
	private Cuenta cuenta;

	//bi-directional one-to-one association to PooledAccount
	@OneToOne(mappedBy="cuentaFintech")
	private PooledAccount pooledAccount;

	//bi-directional one-to-one association to Segregada
	@OneToOne(mappedBy="cuentaFintech")
	private Segregada segregada;

<<<<<<< Updated upstream
=======
	

>>>>>>> Stashed changes
	public CuentaFintech() {
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getClasificacion() {
		return this.clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Object fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Object getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Object fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cuenta getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public PooledAccount getPooledAccount() {
		return this.pooledAccount;
	}

	public void setPooledAccount(PooledAccount pooledAccount) {
		this.pooledAccount = pooledAccount;
	}

	public Segregada getSegregada() {
		return this.segregada;
	}

	public void setSegregada(Segregada segregada) {
		this.segregada = segregada;
	}

<<<<<<< Updated upstream
=======
	
>>>>>>> Stashed changes

}