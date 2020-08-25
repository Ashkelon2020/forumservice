package telran.ashkelon2020.forum.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class NewPostDto {
	String title;
	String content;
	Set<String> tags;
}
