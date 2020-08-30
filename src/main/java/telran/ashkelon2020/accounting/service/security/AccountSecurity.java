package telran.ashkelon2020.accounting.service.security;

public interface AccountSecurity {
	
	String getLogin(String token);
	
	boolean checkExpDate(String login);
	
	boolean checkHaveRole(String login, String role);

}
