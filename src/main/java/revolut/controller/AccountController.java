package revolut.controller;

import revolut.dto.AccountDTO;
import revolut.dto.ResponseDTO;
import revolut.exception.AccountException;
import revolut.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

	@Inject
	private AccountService accountService;

	@POST
	public Response addAccount(AccountDTO a) {
		ResponseDTO response = new ResponseDTO();
		try{
			accountService.save(a);
			return Response.ok().build();
		} catch (AccountException e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(response).build();
		}
		catch (Exception e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.serverError().entity(response).build();
		}
	}
	
	@GET
	@Path("/{accountNumber}")
	public Response getBalance(@PathParam("accountNumber") String accountNumber) {
			AccountDTO account = accountService.findByNumber(accountNumber);
			if(account != null){
				return Response.ok(account).build();	
			} 
			return Response.status(Status.NOT_FOUND).build();
	}
	
	@POST
	@Path("/withdraw/{accountNumber}/{amount}")
	public Response withdraw(@PathParam("accountNumber") String accountNumber, @PathParam("amount") Double amount) {
		ResponseDTO response = new ResponseDTO();
		try{
			accountService.withdraw(new AccountDTO(accountNumber), amount);
			return Response.ok(accountService.findByNumber(accountNumber)).build();
		} catch (AccountException e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(response).build();
		}
		catch (Exception e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.serverError().entity(response).build();
		}
	}
	
	@POST
	@Path("/deposit/{accountNumber}/{amount}")
	public Response deposit(@PathParam("accountNumber") String accountNumber, @PathParam("amount") Double amount) {
		ResponseDTO response = new ResponseDTO();
		try{
			accountService.deposit(new AccountDTO(accountNumber), amount);
			return Response.ok(accountService.findByNumber(accountNumber)).build();
		} catch (AccountException e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(response).build();
		}
		catch (Exception e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
			return Response.serverError().entity(response).build();
		}
	}

}
