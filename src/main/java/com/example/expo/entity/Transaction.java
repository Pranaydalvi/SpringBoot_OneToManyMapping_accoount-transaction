package com.example.expo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    
    private String transactionCode;
    
    private String transactionDate;
    
    private double amountCredited;
    
    private double amountDebited;
    
    private String transactionStatus;
    
    private String beneficiaryAccount;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmountCredited() {
		return amountCredited;
	}

	public void setAmountCredited(double amountCredited) {
		this.amountCredited = amountCredited;
	}

	public double getAmountDebited() {
		return amountDebited;
	}

	public void setAmountDebited(double amountDebited) {
		this.amountDebited = amountDebited;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionCode=" + transactionCode
				+ ", transactionDate=" + transactionDate + ", amountCredited=" + amountCredited + ", amountDebited="
				+ amountDebited + ", transactionStatus=" + transactionStatus + ", beneficiaryAccount="
				+ beneficiaryAccount + "]";
	}
    
    
    

}
