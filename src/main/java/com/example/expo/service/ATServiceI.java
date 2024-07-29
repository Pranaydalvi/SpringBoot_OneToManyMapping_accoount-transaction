package com.example.expo.service;

import java.util.List;

import com.example.expo.entity.Account;
import com.example.expo.entity.Transaction;

public interface ATServiceI {

	int createAccount(Account account);
	
    Account getAccountById(int accountId);
    
    List<Account> getAllAccounts();
    
    Account updateAccount(int accountId, Account account);
    
    boolean deleteAccount(int accountId);
    
    Account findByAccountNumber(String accountNumber);
    
    List<Account> findByAccountType(String accountType);
    
    List<Account> findByActive(boolean active);
    
    List<Account> findByBalanceGreaterThan(double amount);
    
    List<Account> findAccountsByBranchName(String branchName);

	void addTransactionDetails(Transaction txtData, String accno) throws Exception;

	void MailSenderWithAttachments();

	List<Account> generatecsvFileData();

}
