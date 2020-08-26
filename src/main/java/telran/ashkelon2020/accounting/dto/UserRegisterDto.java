package telran.ashkelon2020.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NonNull
public class UserRegisterDto {
	String login;
	String password;
	String firstName;
	String lastName;

}
