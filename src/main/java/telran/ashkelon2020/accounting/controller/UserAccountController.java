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
import telran.ashkelon2020.accounting.dto.UnauthorizedException;
import telran.ashkelon2020.accounting.dto.UserAccountResponseDto;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	@Autowired
	UserAccountService service;

	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return service.addUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserAccountResponseDto login(@RequestHeader("Authorization") String token) {
		UserLoginDto userLoginDto = tokenDecode(token);
		return service.getUser(userLoginDto.getLogin(), userLoginDto.getPassword());
	}

	@PutMapping("/user/{login}")
	public UserAccountResponseDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdateDto) {
		return service.editUser(login, userUpdateDto);
	}

	@DeleteMapping("/user/{login}")
	public UserAccountResponseDto removeUser(@PathVariable String login) {
		return service.removeUser(login);
	}

	@PutMapping("user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return service.changeRolesList(login, role, true);
	}

	@DeleteMapping("user/{login}/role/{role}")
	public RolesResponseDto removeRole(@PathVariable String login, @PathVariable String role) {
		return service.changeRolesList(login, role, false);
	}

	@PutMapping("/user/password")
	public void changePassword(@RequestBody UserLoginDto userLoginDto) {
		service.changePassword(userLoginDto.getLogin(), userLoginDto.getPassword());
	}

	private UserLoginDto tokenDecode(String token) {
		try {
			String[] credentials = token.split(" ");
			String credential = new String(Base64.getDecoder().decode(credentials[1]));
			credentials = credential.split(":");
			return new UserLoginDto(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
	}

}
