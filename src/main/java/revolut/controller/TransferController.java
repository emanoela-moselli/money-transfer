package revolut.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import revolut.dto.ResponseDTO;
import revolut.dto.TransactionDTO;
import revolut.exception.AccountException;
import revolut.service.TransactionService;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {
	
	@Inject
	private TransactionService transactionService;
	
	@PUT
	public Response transfer(TransactionDTO dto) {
		ResponseDTO response = new ResponseDTO();
		try{
			transactionService.transfer(dto);
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
	

}
