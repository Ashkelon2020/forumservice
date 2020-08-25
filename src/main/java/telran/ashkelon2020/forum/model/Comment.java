package telran.ashkelon2020.forum.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = { "user", "dateCreated" })
@ToString
public class Comment {
	String user;
	@Setter
	String message;
	LocalDateTime dateCreated;
	int likes;

	public Comment() {
		dateCreated = LocalDateTime.now();
	}

	public Comment(String user, String message) {
		this();
		this.user = user;
		this.message = message;
	}

	public void addLike() {
		likes++;
	}

}
