package revolut.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import revolut.entity.Account;
import revolut.exception.AccountException;

@Stateless
@Named
public class TransactionDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Inject
	AccountDAO accountDAO;
	
	public void transfer(Account originAccount, Account destinationAccount, Double amount) throws AccountException {
		accountDAO.withdraw(originAccount, amount);
		accountDAO.deposit(destinationAccount, amount);
	}

}
