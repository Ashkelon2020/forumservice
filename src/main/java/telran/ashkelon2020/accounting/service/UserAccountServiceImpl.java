package telran.ashkelon2020.accounting.service;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.accounting.dao.UserAccountRepository;
import telran.ashkelon2020.accounting.dto.RolesResponseDto;
import telran.ashkelon2020.accounting.dto.UserAccountResponseDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.dto.exceptions.UserExistsException;
import telran.ashkelon2020.accounting.dto.exceptions.UserNotFoundException;
import telran.ashkelon2020.accounting.model.UserAccount;

@Service
@ManagedResource
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	ModelMapper modelMapper;

	@Value("${expdate.value}")
	private long period;

	@Value("${default.role}")
	private String defaultUser;

	@ManagedAttribute
	public long getPeriod() {
		return period;
	}

	@ManagedAttribute
	public void setPeriod(long period) {
		this.period = period;
	}

	public String getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	@Override
	public UserAccountResponseDto addUser(UserRegisterDto userRegisterDto) {
		if (repository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException(userRegisterDto.getLogin());
		}
		String hashPassword = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
		userAccount.setExpDate(LocalDateTime.now().plusDays(period));
		userAccount.addRole(defaultUser.toUpperCase());
		userAccount.setPassword(hashPassword);
		repository.save(userAccount);
		return modelMapper.map(userAccount, UserAccountResponseDto.class);
	}

	@Override
	public UserAccountResponseDto getUser(String login) {
		UserAccount userAccount = repository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		return modelMapper.map(userAccount, UserAccountResponseDto.class);
	}

	@Override
	public UserAccountResponseDto removeUser(String login) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		repository.deleteById(login);
		return modelMapper.map(userAccount, UserAccountResponseDto.class);
	}

	@Override
	public UserAccountResponseDto editUser(String login, UserUpdateDto userUpdateDto) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (userUpdateDto.getFirstName() != null) {
			userAccount.setFirstName(userUpdateDto.getFirstName());
		}
		if (userUpdateDto.getLastName() != null) {
			userAccount.setLastName(userUpdateDto.getLastName());
		}
		repository.save(userAccount);
		return modelMapper.map(userAccount, UserAccountResponseDto.class);
	}

	@Override
	public RolesResponseDto changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		boolean res;
		if (isAddRole) {
			res = userAccount.addRole(role.toUpperCase());
		} else {
			res = userAccount.removeRole(role.toUpperCase());
		}
		if (res) {
			repository.save(userAccount);
		}
		
		return modelMapper.map(userAccount, RolesResponseDto.class);
	}

	@Override
	public void changePassword(String login, String password) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now().plusDays(period));
		repository.save(userAccount);
	}

}
