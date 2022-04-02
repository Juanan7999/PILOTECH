package es.uma.proyecto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CUENTA database table.
 * 
 */
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name="disc", discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue("Cuenta")

@NamedQuery(name="Cuenta.findAll", query="SELECT c FROM Cuenta c")
public class Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private String swift;

	

	

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="cuenta1")
	private List<Transaccion> transaccions1;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="cuenta2")
	private List<Transaccion> transaccions2;

	public Cuenta() {
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSwift() {
		return this.swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	

	

	public List<Transaccion> getTransaccions1() {
		return this.transaccions1;
	}

	public void setTransaccions1(List<Transaccion> transaccions1) {
		this.transaccions1 = transaccions1;
	}

	public Transaccion addTransaccions1(Transaccion transaccions1) {
		getTransaccions1().add(transaccions1);
		transaccions1.setCuenta1(this);

		return transaccions1;
	}

	public Transaccion removeTransaccions1(Transaccion transaccions1) {
		getTransaccions1().remove(transaccions1);
		transaccions1.setCuenta1(null);

		return transaccions1;
	}

	public List<Transaccion> getTransaccions2() {
		return this.transaccions2;
	}

	public void setTransaccions2(List<Transaccion> transaccions2) {
		this.transaccions2 = transaccions2;
	}

	public Transaccion addTransaccions2(Transaccion transaccions2) {
		getTransaccions2().add(transaccions2);
		transaccions2.setCuenta2(this);

		return transaccions2;
	}

	public Transaccion removeTransaccions2(Transaccion transaccions2) {
		getTransaccions2().remove(transaccions2);
		transaccions2.setCuenta2(null);

		return transaccions2;
	}

}