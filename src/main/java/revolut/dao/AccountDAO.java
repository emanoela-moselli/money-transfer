package revolut.dao;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import revolut.entity.Account;
import revolut.exception.AccountException;

@Stateless
@Named
public class AccountDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public void save(Account account) throws AccountException {
		entityManager.persist(account);
	}
	
	public Account findById(Long id) {
		return entityManager.find(Account.class, id);
	}
	

	public Double getBalance(Account originAccount) throws AccountException {
		try{
			return (Double) entityManager.createQuery("SELECT a.balance from Account a where a =:_originAccount")
				.setParameter("_originAccount", originAccount).getSingleResult();
		}catch (NoResultException e) {
			throw new AccountException("Invalid Account.");
		}
	}

	public void withdraw(Account account, Double amount) throws AccountException {
		if (getBalance(account) >= amount) {
			entityManager.createQuery("UPDATE Account a SET a.balance =:_balance where a =:_account")
			.setParameter("_balance" , account.getBalance() - amount)
			.setParameter("_account", account)
			.executeUpdate();
		} else{
			throw new AccountException("Insufficient funds on account " +account.getNumber()+ ".");
		}
	}
	
	public void deposit(Account account, Double amount) throws AccountException {
		entityManager.createQuery("UPDATE Account a SET a.balance =:_balance where a =:_account")
		.setParameter("_balance" , account.getBalance() + amount)
		.setParameter("_account", account)
		.executeUpdate();
	}

	public Account findByNumber(String accountNumber) {
		try{
			return (Account)entityManager.createQuery("SELECT a FROM Account a where a.number =:_number").setParameter("_number", accountNumber).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


}
