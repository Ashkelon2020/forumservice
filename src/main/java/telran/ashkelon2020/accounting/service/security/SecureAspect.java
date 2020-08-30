package telran.ashkelon2020.accounting.service.security;

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
			+ " && args(login, token, ..)")
	public void manipulateUser(String login, String token) {
	}
	
	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserAccountController.*Role(..))"
			+ " && args(..,token)")
	public void manipulateRole(String token) {
	}
	
	@Pointcut("@annotation(Authenticated)")
	public void authenticated() {
		
	}

	@Around("loginUser(token)")
	public Object checkTokenAndExpDate(ProceedingJoinPoint pjp, String token) throws Throwable {
		Object[] args = pjp.getArgs();
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		args[args.length - 1] = user;
		return pjp.proceed(args);
	}
	
	@Before("manipulateUser(login, token)")
	public void checkTokenAndExpDateAndValidateUser(String login, String token){
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		if (!user.equals(login)) {
			throw new ForbiddenException();
		}
	}
	
	@Before("manipulateRole(token)")
	public void checkTokenAndValidateAdmin(String token){
		String user = securityService.getLogin(token);
		if (!securityService.checkHaveRole(user, "ADMINISTRATOR")) {
			throw new ForbiddenException();
		}
	}
	
	@Around("authenticated()")
	public Object checkAuthenticated(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String user = securityService.getLogin((String)args[0]);
		args[args.length - 1] = user;
		return pjp.proceed(args);
	}

}
