package telran.ashkelon2020.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9056361396024977676L;
	
	public UserNotFoundException(String login) {
		super("User " + login + " not found");
	}

}
