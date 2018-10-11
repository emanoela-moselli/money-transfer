package revolut.service;

import revolut.dao.AccountDAO;
import revolut.dto.AccountDTO;
import revolut.entity.Account;
import revolut.exception.AccountException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class AccountService {
	
	private AccountDAO accountDAO;
	
	@Inject
	public AccountService(AccountDAO accountDAO){
		this.accountDAO = accountDAO;
	}
	
	public AccountDTO save(AccountDTO accountDTO) throws AccountException {
		if(accountDTO.getBalance() == null || accountDTO.getBalance() < 0 ){
			throw new AccountException("Invalid value of balance.");
		}
		
		if( accountDTO.getNumber() == null  ||  accountDTO.getNumber().trim().equals("")  ){
			throw new AccountException("Invalid account number.");
		}
		
		if(accountDAO.findByNumber(accountDTO.getNumber()) != null){
			throw new AccountException("Account Already Exists");
		}
		
		Account account = new Account(accountDTO);
		accountDAO.save(account);
		return new AccountDTO(account);
	}
	
	public AccountDTO findById(Long id) {
		Account account = accountDAO.findById(id);
		if(account != null){
			return new AccountDTO(account);
		}
		return null;
	}
	
	public AccountDTO findByNumber(String accountNumber) {
		Account account = accountDAO.findByNumber(accountNumber);
		if(account != null){
			return new AccountDTO(account);
		}
		return null;
	}
	
	public Double getBalance(AccountDTO account) throws AccountException {
		return accountDAO.getBalance(new Account(account));
	}

	public void withdraw(AccountDTO accountDTO, Double amount) throws AccountException {
		if(accountDTO == null || accountDTO.getNumber() == null){
			throw new AccountException("Invalid account number.");
		}
		if(amount == null || amount.isNaN() || amount <= 0){
			throw new AccountException("Invalid amount.");
		}
		Account account = accountDAO.findByNumber(accountDTO.getNumber());
		accountDAO.withdraw(account,amount);
	}

	public void deposit(AccountDTO accountDTO, Double amount) throws AccountException {
		if(accountDTO == null || accountDTO.getNumber() == null ){
			throw new AccountException("Invalid account number.");
		}
		if(amount == null || amount.isNaN() || amount <= 0){
			throw new AccountException("Invalid amount.");
		}
		Account account = accountDAO.findByNumber(accountDTO.getNumber());
		accountDAO.deposit(account,amount);
	}

}
