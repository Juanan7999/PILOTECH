package es.uma.proyecto.modelsrest;

public class AccountHolder {
public boolean activeCostumer;
public String accounttype;
//public NombreCliente name;
public DireccionCliente address;

public boolean isActiveCostumer() {
	return activeCostumer;
}
public void setActiveCostumer(boolean activeCostumer) {
	this.activeCostumer = activeCostumer;
}
public String getAccounttype() {
	return accounttype;
}
public void setAccounttype(String accounttype) {
	this.accounttype = accounttype;
}
/*public NombreCliente getName() {
	return name;
}
public void setName(NombreCliente name) {
	this.name = name;
}*/
public DireccionCliente getAddress() {
	return address;
}
public void setAddress(DireccionCliente address) {
	this.address = address;
}
}
