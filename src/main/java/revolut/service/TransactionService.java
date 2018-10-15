package revolut.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import revolut.dao.TransactionDAO;
import revolut.dto.AccountDTO;
import revolut.dto.TransactionDTO;
import revolut.entity.Account;
import revolut.exception.AccountException;

@Named
@Stateless
public class TransactionService {
	
	@Inject
	private TransactionDAO transactionDAO;
	@Inject
	private AccountService accountService;
	
	public void transfer(AccountDTO originAccount, AccountDTO destinationAccount, Double amount) throws AccountException {
		if(originAccount == null || originAccount.getId() == null || accountService.findById(originAccount.getId()) == null 
				|| destinationAccount == null || destinationAccount.getId() == null || accountService.findById(destinationAccount.getId()) == null){
			throw new AccountException("Account not found.");
		}
		
		if(originAccount.equals(destinationAccount)){
			throw new AccountException("Invalid operation for the same account.");
		}
		
		if(amount == null || amount <= 0){
			throw new AccountException("Invalid value.");
		}
		transactionDAO.transfer(new Account(originAccount), new Account(destinationAccount), amount);
	}
	
	public void transfer(TransactionDTO transactionDTO) throws AccountException {
		AccountDTO originAccount;
		AccountDTO destinationAccount;
		if(transactionDTO.getOriginAccount() != null){
			originAccount = accountService.findByNumber(transactionDTO.getOriginAccount());
		} else {
			throw new AccountException("Origin account not found.");
		}
		if(transactionDTO.getDestinationAccount() != null){
			destinationAccount = accountService.findByNumber(transactionDTO.getDestinationAccount());
		} else {
			throw new AccountException("Destination account not found.");
		}
		
		transfer(originAccount, destinationAccount, transactionDTO.getAmount());
		
	}

}
