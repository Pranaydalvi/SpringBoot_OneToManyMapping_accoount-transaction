package com.example.expo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.expo.entity.Account;
import com.example.expo.entity.Transaction;
import com.example.expo.exception.AccountNotActive;
import com.example.expo.exception.AccountNotFound;
import com.example.expo.service.ATServiceI;

@RestController
public class AcTransactionController {

	@Autowired
	private ATServiceI ats;

	@PostMapping(value = "/accountCreate")
	public ResponseEntity<String> createAccoount(@RequestBody Account accountData) {
		System.out.println("Check Account Data : " + accountData);
		int i = ats.createAccount(accountData);
		if (i > 0) {
			return new ResponseEntity<String>("Account Created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Account Not Created", HttpStatus.OK);
		}
	}

	@GetMapping(value = "/accountbyid/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") int accountId) {
		Account account = ats.getAccountById(accountId);
		if (account != null) {
			return new ResponseEntity<>(account, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getaccountByNumber")
	public ResponseEntity<Account> getAccountByNumber(@RequestParam("accountNumber") String accountNumber)
			throws Exception {
		Account account = ats.findByAccountNumber(accountNumber);
		if (account != null) {
			return new ResponseEntity<>(account, HttpStatus.OK);
		} else {
			throw new AccountNotFound("your account number is null");
		}
	}

	@PostMapping(value = "/addTransactionDetails/{accno}")
	public ResponseEntity<String> addTransactionDetails(@RequestBody Transaction txtData, @PathVariable String accno)
			throws Exception {
		System.out.println("Check Acc number And Transaction Data : " + accno + " " + txtData);
		try {
			ats.addTransactionDetails(txtData, accno);
			return new ResponseEntity<>("Operation Done.", HttpStatus.OK);
		} catch (AccountNotActive e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping(value="/MailSenderWithAttachments")
	public ResponseEntity<String> MailSenderWithAttachments(){
		ats.MailSenderWithAttachments();
		return new ResponseEntity<String>("mail send",HttpStatus.OK);
	}

	@RequestMapping(value = "/generateCSV")
	public void generateCSVFile(HttpServletResponse response) throws IOException {
		List<Account> list =  ats.generatecsvFileData();


		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String txtDate = dateFormat.format(date);

		response.setContentType("text/csv");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=accountData_"+txtDate+".csv";

		response.setHeader(headerKey, headerValue);

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] csvName = {"ID","ACCOUNT_NUMBER","ACCOUNT_TYPE","IFSC_CODE","ACCOUNT_HOLDER_NAME","BRANCH_NAME","TOTAL_BALANCE","OPENING_DATE","STATUS","EMAIL_ID"};

		String[] csvMapping = {"accountId","accountNumber","accountType","ifscCode","accountHolderName","branchName","balance","openingDate","active","email"};

		csvWriter.writeHeader(csvName);

		for(Account accountData : list) {
			csvWriter.write(accountData, csvMapping);
		}
		csvWriter.close();
	}

	
	@GetMapping(value = "/getallaccounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = ats.getAllAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@PutMapping(value = "/updatebyaccountid/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable("id") int accountId, @RequestBody Account account) {
		Account updatedAccount = ats.updateAccount(accountId, account);
		if (updatedAccount != null) {
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/deleteaccountbyid/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable("id") int accountId) {
		boolean isDeleted = ats.deleteAccount(accountId);
		if (isDeleted) {
			return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getaccountsByType")
	public ResponseEntity<List<Account>> getAccountsByType(@RequestParam("accountType") String accountType) {
		List<Account> accounts = ats.findByAccountType(accountType);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping(value = "/getactiveAccounts")
	public ResponseEntity<List<Account>> getActiveAccounts(@RequestParam("active") boolean active) {
		List<Account> accounts = ats.findByActive(active);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping(value = "/getaccountsByBalance")
	public ResponseEntity<List<Account>> getAccountsByBalance(@RequestParam("amount") double amount) {
		List<Account> accounts = ats.findByBalanceGreaterThan(amount);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping(value = "/getaccountsByBranch")
	public ResponseEntity<List<Account>> getAccountsByBranch(@RequestParam("branchName") String branchName) {
		List<Account> accounts = ats.findAccountsByBranchName(branchName);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

}
