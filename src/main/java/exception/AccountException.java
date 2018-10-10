package exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8694645760779381677L;
	
	public AccountException(String errorMessage) {
        super(errorMessage);
    }
}
