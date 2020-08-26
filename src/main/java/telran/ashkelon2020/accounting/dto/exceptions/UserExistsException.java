package telran.ashkelon2020.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1615691789522973687L;
	
	public UserExistsException(String login) {
		super("User " + login +" exists");
	}

}
