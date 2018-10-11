package revolut.moneyTransfer.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import revolut.dto.AccountDTO;
import revolut.exception.AccountException;
import revolut.service.AccountService;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.OptimisticLockException;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class AccountTest extends BaseTest{
	
	private static Context context;
	private static AccountService accountService;

	@BeforeClass
	public static void setup() throws NamingException{
		context = EJBContainer.createEJBContainer(getProperties()).getContext();

		accountService = (AccountService) context.lookup("java:global/moneyTransfer/AccountService");
	}

	@AfterClass
	public static void tearDown() throws NamingException{
		if (context != null) {
			context.close();
		}
	}

	@Test
	public void testSave() throws Exception {
		AccountDTO account = new AccountDTO("989", 100d);
		account = accountService.save(account);
		AccountDTO recoveredAccount = accountService.findById(account.getId());
		assertEquals(recoveredAccount.getNumber(),account.getNumber());
	}
	
	@Test(expected=AccountException.class)
	public void testSaveAccountWithNegativeBalance() throws Exception {
		AccountDTO account = new AccountDTO("990", -100d);
		account = accountService.save(account);
	}
	
	@Test(expected=AccountException.class)
	public void testSaveAccountWithNullBalance() throws Exception {
		AccountDTO account = new AccountDTO("993", null);	
		account = accountService.save(account);
	}
	
	@Test(expected=AccountException.class)
	public void testSaveAccountAlreadExistent() throws Exception {
		AccountDTO account = new AccountDTO("994", 100d);	
		account = accountService.save(account);
		AccountDTO account2 = new AccountDTO("994", 600d);	
		account = accountService.save(account2);
	}
	
	@Test(expected=AccountException.class)
	public void testSaveAccountWithInvalidNumber() throws Exception {
		AccountDTO account = new AccountDTO(null, 100d);	
		account = accountService.save(account);
	}
	
	@Test
	public void testWithdrawValidAmmount() throws Exception {
		AccountDTO account = new AccountDTO("995", 100d);	
		account = accountService.save(account);
		accountService.withdraw(account, 50d);
		assertEquals(accountService.getBalance(account), Double.valueOf(50d));
	}
	
	@Test(expected=AccountException.class)
	public void testWithdrawInsuficientFunds() throws Exception {
		AccountDTO account = new AccountDTO("996", 100d);	
		account = accountService.save(account);
		accountService.withdraw(account, 150d);
	}
	
	@Test(expected=AccountException.class)
	public void testWithdrawInvalidValue() throws Exception {
		AccountDTO account = new AccountDTO("997", 100d);	
		account = accountService.save(account);
		accountService.withdraw(account, -150d);
	}
	
	@Test(expected=AccountException.class)
	public void testWithdrawNullValue() throws Exception {
		AccountDTO account = new AccountDTO("998", 100d);	
		account = accountService.save(account);
		accountService.withdraw(account, null);
	}
	
	@Test(expected=AccountException.class)
	public void testWithdrawNullAccount() throws Exception {
		accountService.withdraw(null, 100d);
	}
	
	@Test
	public void testDepositValidAmmount() throws Exception {
		AccountDTO account = new AccountDTO("1001", 100d);	
		account = accountService.save(account);
		accountService.deposit(account, 50d);
		assertEquals(accountService.getBalance(account), Double.valueOf(150d));
	}
	
	
	@Test(expected=AccountException.class)
	public void testDepositInvalidValue() throws Exception {
		AccountDTO account = new AccountDTO("1002", 100d);	
		account = accountService.save(account);
		accountService.deposit(account, -150d);
	}
	
	@Test(expected=AccountException.class)
	public void testDepositNullValue() throws Exception {
		AccountDTO account = new AccountDTO("1003", 100d);	
		account = accountService.save(account);
		accountService.deposit(account, null);
	}
	
	@Test(expected=AccountException.class)
	public void testDepositNullAccount() throws Exception {
		accountService.deposit(null, 100d);
	}
}
