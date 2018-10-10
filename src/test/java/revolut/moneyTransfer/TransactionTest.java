package revolut.moneyTransfer;

import java.util.HashMap;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.AccountDTO;
import exception.AccountException;
import junit.framework.TestCase;
import service.IAccountService;
import service.ITransactionService;


@RunWith(JUnit4.class)
public class TransactionTest extends TestCase {

	static ITransactionService transactionService ;
	static IAccountService accountService;

	@BeforeClass
	public static void getServices() throws NamingException{
		final java.util.Map<String, String> p = new  HashMap<String,String>();
		p.put("h2DataBase", "new://Resource?type=DataSource");
		p.put("h2DataBase.JdbcDriver", "org.h2.Driver");
		p.put("h2DataBase.JdbcUrl", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		p.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
		p.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
		p.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

		final Context context = EJBContainer.createEJBContainer(p).getContext();

		transactionService = (ITransactionService) context.lookup("java:global/moneyTransfer/TransactionService");
		accountService = (IAccountService) context.lookup("java:global/moneyTransfer/AccountService");
	}


	@Test
	public void testTransferHapyPath() throws Exception {

		AccountDTO originAccount = new AccountDTO("123", 100d);	
		originAccount = accountService.save(originAccount);

		AccountDTO destinationAccount = new AccountDTO("321", 10d);
		destinationAccount = accountService.save(destinationAccount);

		transactionService.transfer(originAccount, destinationAccount, 10d);

		assertEquals(accountService.getBalance(destinationAccount), 20d);
		assertEquals(accountService.getBalance(originAccount), 90d);
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
