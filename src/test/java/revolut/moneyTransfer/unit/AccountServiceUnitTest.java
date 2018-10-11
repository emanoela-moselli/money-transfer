package revolut.moneyTransfer.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import revolut.dao.AccountDAO;
import revolut.dto.AccountDTO;
import revolut.entity.Account;
import revolut.exception.AccountException;
import revolut.service.AccountService;

@RunWith(JUnit4.class)
public class AccountServiceUnitTest {
	
	@Mock
	private AccountDAO accountDAO;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); 
	
	@Test
	public void testFindByNumberReturningNull(){
		when(accountDAO.findByNumber("1234")).thenReturn(null);
		
		AccountService service = new AccountService(accountDAO);
		
		assertNull(service.findByNumber("1234"));
	}
	
	@Test
	public void testFindByNumberReturningAccount(){
		when(accountDAO.findByNumber("1234")).thenReturn(new Account(1l, "1234", 110d));
		
		AccountService service = new AccountService(accountDAO);
		
		assertEquals(new AccountDTO(1l, "1234", 110d), service.findByNumber("1234"));
	}
	
	@Test
	public void testFindByIdReturningNull(){
		when(accountDAO.findById(1l)).thenReturn(null);
		
		AccountService service = new AccountService(accountDAO);
		
		assertNull(service.findById(1l));
	}
	
	@Test
	public void testFindByIdReturningAccount(){
		when(accountDAO.findById(1l)).thenReturn(new Account(1l, "1234", 110d));
		
		AccountService service = new AccountService(accountDAO);
		
		assertEquals(new AccountDTO(1l, "1234", 110d), service.findById(1l));
	}
	
	@Test(expected = AccountException.class)
	public void testeGetBalanceNoResultException() throws AccountException{
		when(accountDAO.getBalance(new Account(1l, "1234", 110d))).thenThrow(AccountException.class);
		
		AccountService service = new AccountService(accountDAO);
		
		service.getBalance(new AccountDTO(1l, "1234", 110d));
	}

}
