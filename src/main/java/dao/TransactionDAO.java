package dao;

import java.util.Calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Account;
import exception.AccountException;

@Stateless
@Named
public class TransactionDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Inject
	AccountDAO accountDAO;
	
	public void transfer(Account originAccount, Account destinationAccount, Double amount) throws AccountException {
		accountDAO.withdrall(originAccount, amount);
		accountDAO.deposit(destinationAccount, amount);
			
			Calendar transactionDate = Calendar.getInstance();
			
//			entityManager.persist(new Transaction(transactionDate, ETransaction.OUTGOING_TRANSFER, originAccount,destinationAccount));
//			entityManager.persist(new Transaction(transactionDate, ETransaction.INCOMING_TRANSFER, destinationAccount,originAccount));
			
	}

}
