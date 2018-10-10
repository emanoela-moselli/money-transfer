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


@RunWith(JUnit4.class)
public class AccountTest extends TestCase {

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

		accountService = (IAccountService) context.lookup("java:global/moneyTransfer/AccountService");
	}


	@Test
	public void testSave() throws Exception {
		AccountDTO account = new AccountDTO("991", 100d);	
		account = accountService.save(account);
		AccountDTO recoveredAccount = accountService.findById(account.getId());
		assertEquals(recoveredAccount.getNumber(),account.getNumber());
	}
	
	@Test(expected=AccountException.class)
	public void testSaveAccountWithNegativeBalance() throws Exception {
		AccountDTO account = new AccountDTO("992", -100d);	
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
	
}
