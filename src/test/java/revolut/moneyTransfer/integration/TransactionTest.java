package revolut.moneyTransfer.integration;

import junit.framework.TestCase;
import org.junit.Before;
import revolut.dto.AccountDTO;
import revolut.exception.AccountException;
import revolut.service.AccountService;
import revolut.service.TransactionService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;


@RunWith(JUnit4.class)
public class TransactionTest extends BaseTest{
	
	private static Context context;

	private static TransactionService transactionService ;
	private static AccountService accountService;

	@BeforeClass
	public static void setup() throws NamingException{
		context = EJBContainer.createEJBContainer(getProperties()).getContext();

		transactionService = (TransactionService) context.lookup("java:global/moneyTransfer/TransactionService");
		accountService = (AccountService) context.lookup("java:global/moneyTransfer/AccountService");
	}

	@AfterClass
	public static void tearDown() throws NamingException {
		if (context != null) {
			context.close();
		}
	}

	@Test
	public void testTransferHapyPath() throws Exception {

		AccountDTO originAccount = new AccountDTO("123", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("321", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 10d);

		assertEquals(accountService.getBalance(destinationAccount), Double.valueOf(20d));
		assertEquals(accountService.getBalance(originAccount), Double.valueOf(90d));
	}
	
	@Test
	public void testTransferHapyPathWithDecimals() throws Exception {

		AccountDTO originAccount = new AccountDTO("111", 100.99d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("222", 10.87d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 5.55d);

		assertEquals(accountService.getBalance(destinationAccount), 16.42d, 0.001);
		assertEquals(accountService.getBalance(originAccount), 95.44d, 0.001);
	}

	
	@Test(expected=AccountException.class)
	public void testTransferNonexistentOriginAccount() throws AccountException{
		AccountDTO originAccount = new AccountDTO("333", 100d);	
		
		AccountDTO destinationAccount = new AccountDTO("444", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 10d);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferNullOriginAccount() throws AccountException{
		
		AccountDTO destinationAccount = new AccountDTO("555", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(null, destinationAccount, 10d);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferNonexistentDestinationAccount() throws AccountException{
		AccountDTO originAccount = new AccountDTO("666", 100d);	
		originAccount = accountService.save(originAccount);
		
		AccountDTO destinationAccount = new AccountDTO("777", 10d);

		transactionService.transfer(originAccount, destinationAccount, 10d);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferNullDestinationAccount() throws AccountException{
		AccountDTO originAccount = new AccountDTO("888", 100d);	
		originAccount = accountService.save(originAccount);
		
		transactionService.transfer(originAccount, null, 10d);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferToTheSameAccount() throws Exception {
		AccountDTO originAccount = new AccountDTO("999", 100d);	
		originAccount = accountService.save(originAccount);
		transactionService.transfer(originAccount, originAccount, 10d);

	}
	
	@Test(expected=AccountException.class)
	public void testTransferAllFunds() throws AccountException{
		AccountDTO originAccount = new AccountDTO("112", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("221", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 100d);
		
		assertEquals(accountService.getBalance(originAccount), Double.valueOf(0d));
		assertEquals(accountService.getBalance(destinationAccount), Double.valueOf(110d));
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferWithInsufficientFunds() throws AccountException{
		AccountDTO originAccount = new AccountDTO("112", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("221", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 200d);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferWithNullAmmount() throws AccountException{
		AccountDTO originAccount = new AccountDTO("113", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("331", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, null);
	}
	
	
	@Test(expected=AccountException.class)
	public void testTransferWithInvalidAmmount() throws AccountException{
		AccountDTO originAccount = new AccountDTO("114", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("441", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, -10d);
	}
	

}
