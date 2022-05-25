package es.uma.proyecto.modelsrest;

import java.util.List;

public class RespuestaIndividual {
	
public List<ProductoCliente> products;
public String activeCostumer;
public String identificationNumber;
public String dateOfBirth;
public NombreCliente name;
public DireccionCliente address;

public List<ProductoCliente> getProducts() {
	return products;
}
public void setProducts(List<ProductoCliente> products) {
	this.products = products;
}
public String getActiveCostumer() {
	return activeCostumer;
}
public void setActiveCostumer(String activeCostumer) {
	this.activeCostumer = activeCostumer;
}
public String getIdentificationNumber() {
	return identificationNumber;
}
public void setIdentificationNumber(String identificationNumber) {
	this.identificationNumber = identificationNumber;
}
public String getDateOfBirth() {
	return dateOfBirth;
}
public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public NombreCliente getName() {
	return name;
}
public void setName(NombreCliente name) {
	this.name = name;
}
public DireccionCliente getAddress() {
	return address;
}
public void setAddress(DireccionCliente address) {
	this.address = address;
}

}
