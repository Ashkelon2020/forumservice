package telran.ashkelon2020.accounting.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.accounting.dto.RolesResponseDto;
import telran.ashkelon2020.accounting.dto.UserAccountResponseDto;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.dto.exceptions.ForbiddenException;
import telran.ashkelon2020.accounting.dto.exceptions.UnauthorizedException;
import telran.ashkelon2020.accounting.service.UserAccountService;
import telran.ashkelon2020.accounting.service.security.AccountSecurity;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	@Autowired
	UserAccountService accountService;
	
	@Autowired
	AccountSecurity securityService;

	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserAccountResponseDto login(@RequestHeader("Authorization") String token) {
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		return accountService.getUser(user);
	}

	@PutMapping("/user/{login}")
	public UserAccountResponseDto updateUser(@PathVariable String login,
			@RequestBody UserUpdateDto userUpdateDto, @RequestHeader("Authorization") String token) {
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		if (!user.equals(login)) {
			throw new ForbiddenException();
		}
		return accountService.editUser(login, userUpdateDto);
	}

	@DeleteMapping("/user/{login}")
	public UserAccountResponseDto removeUser(@PathVariable String login, @RequestHeader("Authorization") String token) {
		String user = securityService.getLogin(token);
		if (!user.equals(login)) {
			throw new ForbiddenException();
		}
		return accountService.removeUser(login);
	}

	//FIXME
	@PutMapping("user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, true);
	}

	//FIXME
	@DeleteMapping("user/{login}/role/{role}")
	public RolesResponseDto removeRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, false);
	}

	@PutMapping("/user/password")
	public void changePassword(@RequestHeader("Authorization") String token,
			@RequestHeader("X-Password") String newPassword) {
		String user = securityService.getLogin(token);
		accountService.changePassword(user, newPassword);
	}


}
