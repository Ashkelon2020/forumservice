package telran.ashkelon2020.forum.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = { "id" })
@ToString
@Document(collection = "posts")
public class Post {
	String id;
	@Setter
	String title;
	@Setter
	String content;
	String author;
	LocalDateTime dateCreated;
	Set<String> tags;
	int likes;
	Set<Comment> comments;

	public Post() {
		comments = new HashSet<>();
		tags = new HashSet<>();
		dateCreated = LocalDateTime.now();
	}

	public Post(String title, String content, String author) {
		this();
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Post(String title, String content, String author, Set<String> tags) {
		this(title, content, author);
		this.tags = tags;
	}

	public void addLike() {
		likes++;
	}

	public boolean addComment(Comment comment) {
		return comments.add(comment);
	}

	public boolean addTag(String tag) {
		return tags.add(tag);
	}

	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}

}
