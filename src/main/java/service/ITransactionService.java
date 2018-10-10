package service;

import javax.ejb.Local;

import dto.AccountDTO;
import exception.AccountException;

@Local
public interface ITransactionService {
	
	public void transfer(AccountDTO origenAccount, AccountDTO destinationAccount, Double amount) throws AccountException;
	
}
