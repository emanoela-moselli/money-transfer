package service;

import javax.ejb.Local;
import javax.inject.Named;

import dto.AccountDTO;
import exception.AccountException;

@Local
@Named
public interface IAccountService {
	
	public AccountDTO save(AccountDTO accountDTO) throws AccountException;
	
	public Double getBalance(AccountDTO account);
	
	public AccountDTO findById(Long id);
	
	public AccountDTO findByNumber(String accountNumber);
	
	public AccountDTO withdrall(AccountDTO account, Double amount) throws AccountException;
	
	public AccountDTO deposit(AccountDTO account, Double amount) throws AccountException;


}
