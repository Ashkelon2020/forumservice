package telran.ashkelon2020.accounting.service.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.accounting.dto.exceptions.ForbiddenException;

@Aspect
@Component
public class SecureAspect {

	@Autowired
	AccountSecurity securityService;

	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserAccountController.login(..))"
			+ "&& args(token, ..)")
	public void loginUser(String token) {
	}
	
	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserAccountController.*User(..))"
			+ "&& args(token, login, ..)")
	public void manipulateUser(String token, String login) {
	}

	@Around("loginUser(token)")
	public Object checkTokenAndExpDate(ProceedingJoinPoint pjp, String token) throws Throwable {
		Object[] args = pjp.getArgs();
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		args[args.length - 1] = user;
		return pjp.proceed(args);
	}
	
	@Before("manipulateUser(token, login)")
	public void checkTokenAndExpDateAndValidateUser(String token, String login){
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		if (!user.equals(login)) {
			throw new ForbiddenException();
		}
	}

}
