package com.example.expo.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.LongStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.expo.entity.Account;
import com.example.expo.entity.Transaction;
import com.example.expo.exception.AccountNotActive;
import com.example.expo.repo.ATRepository;

@Service
public class ATServiceIMPL implements ATServiceI {

	@Autowired
	private ATRepository atr;

	@Autowired
	private JavaMailSender jms;

	@Override
	public int createAccount(Account account) {
		String accountNumber;
		String prefix = "HDFC";
		int number = 10000000;
		long count = atr.count();
		count++;
		long acno = count + number;
		accountNumber = prefix + acno;
		account.setAccountNumber(accountNumber);

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String openingDate = dateFormat.format(date);

		account.setOpeningDate(openingDate);

		Account accountData = atr.save(account);
		if (accountData != null) {

			SimpleMailMessage message = new SimpleMailMessage();

			message.setTo("pramodkhandare0050@gmail.com");// "andhalekrushna101@gmail.com"

			message.setSubject("Thanks For Creating Account.");

			message.setText("Hi Team,  Thank you for Getting Our Service. Your Account Succeassfully Created.");

			jms.send(message);

		}

		return accountData.getAccountId();
	}

//	We create a LongStream with one value by specifying the stream size as 1.
//	We use findFirst().getAsLong() to retrieve the first (and only) value from the stream.
//	We convert the long value to a string using Long.toString().
//	Finally, we print the string representation of the long number.
	@Override
	public void addTransactionDetails(Transaction txtData, String accno) throws Exception {
		Account accountData = findByAccountNumber(accno);
	    if (accountData != null) {
	        if (!accountData.isActive()) {
	            throw new AccountNotActive("Your Account is Inactive Transaction Not Stored");
	        }

	        Random random = new Random();
	        LongStream lStream = random.longs(1, 0, 999999);
	        long l = lStream.findFirst().getAsLong();
	        String l1 = Long.toString(l);
	        txtData.setTransactionCode(l1);

	        Date date = new Date(System.currentTimeMillis());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	        String txtDate = dateFormat.format(date);
	        txtData.setTransactionDate(txtDate);

	        if (txtData.getAmountCredited() > 0) { // Credit transaction
	        	double balance;
	            balance= accountData.getBalance() + txtData.getAmountCredited();
	            accountData.setBalance(balance);
	            txtData.setTransactionStatus("Success");
	        } else if (txtData.getAmountDebited() > 0) { // Debit transaction
	            if (accountData.getBalance() >= txtData.getAmountDebited()) {
	                if (addOtherAccountTxt(txtData.getBeneficiaryAccount())) {
	                	double balance;
	    	            balance= accountData.getBalance() - txtData.getAmountDebited();
	    	            accountData.setBalance(balance);
	                    txtData.setTransactionStatus("Success");
	                } else {
	                    txtData.setTransactionStatus("FailedANE"); // Account Not Exists
	                }
	            } else {
	                txtData.setTransactionStatus("Failed");
	            }
	        }
	        accountData.getTransactions().add(txtData);
	        atr.save(accountData);
	    }
	}

	private boolean addOtherAccountTxt(String otheraccount) {

		Account accountData = new Account();
		accountData.setAccountNumber("12345678");

		// If the account number matches, return true indicating success
		return otheraccount.equals(accountData.getAccountNumber());
	}

	@Override
	public Account findByAccountNumber(String accountNumber) {
		Account acc = null;
		acc = atr.findByAccountNumber(accountNumber);
		return acc;
	}

	@Override
	public void MailSenderWithAttachments() {
		List<String> emails= atr.gettingmails();
		String[] strarr= new String[emails.size()];
		
		for(int i=0;i<emails.size();i++) {
			strarr[i]=emails.get(i);
		}
		
		
		   try{
			   MimeMessage message = jms.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(strarr);//andhalekrushna101@gmail.com
			helper.setText("Mail with your attachment");
			helper.setSubject("attachment");
			 String[] attachmentPaths = {"H:/resumes/PranayDalvi_Resume.pdf",
		                "H:/resumes/pranaydalvi122@_resume@.pdf",
		                "H:/resumes/pranaydalvi122@resume.pdf"
		            };
			for (String path : attachmentPaths) {
                File attachment = new File(path);
                helper.addAttachment(attachment.getName(), attachment);
            }
			helper.addAttachment("attachment.pdf",new File("H:/resumes/PranayDalvi_Resume.pdf"));
			jms.send(message);
			
		   }catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Account> generatecsvFileData() {
			  List<Account> list = atr.findAll(Sort.by("email").and(Sort.by("accountNumber")).ascending());
			  System.out.println(list);
			  return list;	}
	
	@Override
	public Account getAccountById(int accountId) {
		Optional<Account> account = atr.findById(accountId);
		return account.orElse(null);
	}

	@Override
	public List<Account> getAllAccounts() {
		return atr.findAll();
	}

	@Override
	public Account updateAccount(int accountId, Account account) {

		return null;
	}

	@Override
	public boolean deleteAccount(int accountId) {
		if (atr.existsById(accountId)) {
			atr.deleteById(accountId);
			return true;
		}
		return false;
	}

	@Override
	public List<Account> findByAccountType(String accountType) {
		return atr.findByAccountType(accountType);
	}

	@Override
	public List<Account> findByActive(boolean active) {
		return atr.findByActive(active);
	}

	@Override
	public List<Account> findByBalanceGreaterThan(double amount) {
		return atr.findByBalanceGreaterThan(amount);
	}

	@Override
	public List<Account> findAccountsByBranchName(String branchName) {
		return atr.findAccountsByBranchName(branchName);
	}

}
