package telran.ashkelon2020.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 181617154017719144L;

}
