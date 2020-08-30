package telran.ashkelon2020;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.ashkelon2020.accounting.dao.UserAccountRepository;
import telran.ashkelon2020.accounting.model.UserAccount;

@SpringBootApplication
public class ForumServiceApplication implements CommandLineRunner{
	
	@Autowired
	UserAccountRepository userAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userAccountRepository.existsById("admin")) {
			String hashPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
			UserAccount admin = new UserAccount();
			admin.setLogin("admin");
			admin.setPassword(hashPassword);
			admin.addRole("USER");
			admin.addRole("MODERATOR");
			admin.addRole("ADMINISTRATOR");
			admin.setExpDate(LocalDateTime.now().plusYears(25));
			admin.setFirstName("Administrator");
			admin.setLastName("Administrator");
			userAccountRepository.save(admin);
		}
	}

}
