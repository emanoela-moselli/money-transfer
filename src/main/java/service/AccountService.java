package service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.AccountDAO;
import dto.AccountDTO;
import entity.Account;
import exception.AccountException;

@Stateless
public class AccountService implements IAccountService {
	
	@Inject
	private AccountDAO accountDAO;
	
	public AccountDTO save(AccountDTO accountDTO) throws AccountException {
		if(accountDTO.getBalance() == null || accountDTO.getBalance() < 0){
			throw new AccountException("Invalid value of balance.");
		}
		
		if(accountDTO.getNumber() == null ){
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
	
	public Double getBalance(AccountDTO account) {
		return accountDAO.getBalance(new Account(account));
	}

	public AccountDTO withdrall(AccountDTO accountDTO, Double amount) throws AccountException {
		Account account = new Account(accountDTO);
		accountDAO.withdrall(account,amount);
		return new AccountDTO(account);
	}

	public AccountDTO deposit(AccountDTO accountDTO, Double amount) throws AccountException {
		Account account = new Account(accountDTO);
		accountDAO.deposit(account,amount);
		return new AccountDTO(account);
	}

}
