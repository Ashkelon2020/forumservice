package telran.ashkelon2020.accounting.service;

import telran.ashkelon2020.accounting.dto.RolesResponseDto;
import telran.ashkelon2020.accounting.dto.UserAccountResponseDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;

public interface UserAccountService {

	UserAccountResponseDto addUser(UserRegisterDto userRegisterDto);

	UserAccountResponseDto getUser(String login);
	
	UserAccountResponseDto removeUser(String login);
	
	UserAccountResponseDto editUser(String login, UserUpdateDto userUpdateDto);
	
	RolesResponseDto changeRolesList(String login, String role, boolean isAddRole);
	
	void changePassword(String login, String password);
	
}
