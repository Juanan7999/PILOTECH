package es.uma.proyecto.modelsrest;

public class PeticionClientes {
public NombreCliente name;
public String startPeriod;
public String endPeriod;

public NombreCliente getName() {
	return name;
}
public void setName(NombreCliente name) {
	this.name = name;
}
public String getStartPeriod() {
	return startPeriod;
}
public void setStartPeriod(String startPeriod) {
	this.startPeriod = startPeriod;
}
public String getEndPeriod() {
	return endPeriod;
}
public void setEndPeriod(String endPeriod) {
	this.endPeriod = endPeriod;
}

}
