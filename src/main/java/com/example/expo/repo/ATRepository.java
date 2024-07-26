package com.example.expo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.expo.entity.Account;

@Repository
public interface ATRepository extends JpaRepository<Account, Integer>{

	Account findByAccountNumber(String accountNumber);

    List<Account> findByAccountType(String accountType);

    List<Account> findByActive(boolean active);

    List<Account> findByBalanceGreaterThan(double amount);

    @Query("SELECT a FROM Account a WHERE a.branchName = ?1")
    List<Account> findAccountsByBranchName(String branchName);
    
    @Query(value="select email from account",nativeQuery = true)
    List<String> gettingmails();

}
