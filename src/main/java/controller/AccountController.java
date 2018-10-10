package controller;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.AccountDTO;
import dto.ResponseDTO;
import exception.AccountException;
import service.IAccountService;

@Singleton
@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

	@Inject
	private IAccountService accountService;

	@POST
	@Path("/add")
	public ResponseDTO addAccount(AccountDTO a) {
		ResponseDTO response = new ResponseDTO();
		try{
			accountService.save(a);
			response.setStatus(true);
			response.setMessage("Account created successfully");
		} catch (AccountException e) {
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatus(false);
			response.setMessage("Erros creating the account.");
		}
		return response;
	}
	
	@GET
	@Path("/list")
	public ResponseDTO listAccount() {
		ResponseDTO response = new ResponseDTO();
		try{
			response.setStatus(true);
			response.setMessage("It worked");
		} 
		catch (Exception e) {
			response.setStatus(false);
			response.setMessage("Error listing");
		}
		return response;
	}

}
