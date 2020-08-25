package telran.ashkelon2020.forum.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2701569702621294192L;
	
	public PostNotFoundException(String id) {
		super("Post with id = " + id+" not found");

	}

}
