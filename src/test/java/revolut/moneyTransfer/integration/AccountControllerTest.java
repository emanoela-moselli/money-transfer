package revolut.moneyTransfer.integration;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.OpenEjbContainer;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import revolut.dto.AccountDTO;
import revolut.dto.ResponseDTO;
import revolut.dto.TransactionDTO;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class AccountControllerTest extends BaseTest  {

	private static Context context;

	@BeforeClass
	public static void start() throws NamingException {
		
		context = EJBContainer.createEJBContainer(getProperties()).getContext();
	}

	@AfterClass
	public static void tearDown() throws NamingException {
		if (context != null) {
			context.close();
		}
	}

	@Test
	public void testAddAccount() throws Exception {
		AccountDTO account = new AccountDTO("991", 100d);	
		final Response response = webClient().path("/account").post(new ObjectMapper().writeValueAsString(account));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetBalance() throws Exception {
		AccountDTO account = new AccountDTO("991", 100d);	
		webClient().path("/account").post(new ObjectMapper().writeValueAsString(account));
		
		final Response response = webClient().path("/account/" + account.getNumber()).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testWithdraw() throws Exception {
		AccountDTO account = new AccountDTO("991", 100d);	
		webClient().path("/account").post(new ObjectMapper().writeValueAsString(account));
		
		final Response response =webClient().path("/account/withdraw/"+ account.getNumber() +"/"+ 50d).post(null);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeposit() throws Exception {
		AccountDTO account = new AccountDTO("991", 100d);	
		webClient().path("/account").post(new ObjectMapper().writeValueAsString(account));
		
		final Response response =webClient().path("/account/deposit/"+ account.getNumber() +"/"+ 50d).post(null);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	public void testTransfer() throws Exception {
		AccountDTO originAccount = new AccountDTO("5513", 100d);
		webClient().path("/account").post(Entity.json(originAccount), ResponseDTO.class);
		AccountDTO destinationAccount = new AccountDTO("5512", 100d);
		webClient().path("/account").post(Entity.json(destinationAccount), ResponseDTO.class);

		TransactionDTO account = new TransactionDTO("5513", "5512",50d);	
		final ResponseDTO response = webClient().path("/account/transfer").post(Entity.json(account), ResponseDTO.class);
		assertTrue(response.isStatus());
	}

	private WebClient webClient() {
		return WebClient.create("http://localhost:4204/moneyTransfer")
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE);
	}



}
