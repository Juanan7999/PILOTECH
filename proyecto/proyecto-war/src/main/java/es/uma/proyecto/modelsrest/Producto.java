package es.uma.proyecto.modelsrest;

public class Producto {
public AccountHolder accountHolder;
public String productNumber;
public String status;
public String startDate;
public String endDate;
public AccountHolder getAccountHolder() {
	return accountHolder;
}
public void setAccountHolder(AccountHolder accountHolder) {
	this.accountHolder = accountHolder;
}
public String getProductNumber() {
	return productNumber;
}
public void setProductNumber(String productNumber) {
	this.productNumber = productNumber;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}



}
