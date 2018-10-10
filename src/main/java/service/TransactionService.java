package service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.TransactionDAO;
import dto.AccountDTO;
import entity.Account;
import exception.AccountException;

@Stateless
public class TransactionService implements ITransactionService {
	
	@Inject
	private TransactionDAO transactionDAO;
	@Inject
	private IAccountService accountService;
	
	public void transfer(AccountDTO originAccount, AccountDTO destinationAccount, Double amount) throws AccountException {
		if(originAccount == null || originAccount.getId() == null || accountService.findById(originAccount.getId()) == null 
				|| destinationAccount == null || destinationAccount.getId() == null || accountService.findById(destinationAccount.getId()) == null){
			throw new AccountException("Nonexistent account.");
		}
		
		if(originAccount.equals(destinationAccount)){
			throw new AccountException("Invalid operation for the same account.");
		}
		
		if(amount == null || amount <= 0){
			throw new AccountException("Invalid value.");
		}
		transactionDAO.transfer(new Account(originAccount), new Account(destinationAccount), amount);
	}

}
