package telran.ashkelon2020.forum.service;

import java.util.List;

import telran.ashkelon2020.forum.dto.CommentDto;
import telran.ashkelon2020.forum.dto.DatePeriodDto;
import telran.ashkelon2020.forum.dto.NewCommentDto;
import telran.ashkelon2020.forum.dto.NewPostDto;
import telran.ashkelon2020.forum.dto.PostDto;

public interface ForumService {
	PostDto addNewPost(NewPostDto newPost, String author);

	PostDto getPost(String id);

	PostDto removePost(String id);

	PostDto updatePost(NewPostDto postUpdateDto, String id);

	boolean addLike(String id);

	PostDto addComment(String id, String author, NewCommentDto newCommentDto);

	Iterable<PostDto> findPostsByAuthor(String author);

	Iterable<PostDto> findPostsByTags(List<String> tags);
	
	Iterable<PostDto> findPostsByDates(DatePeriodDto datePeriodDto);
	
	Iterable<CommentDto> findAllPostComments(String id);
	
	Iterable<CommentDto> findAllPostCommentsByAuthor(String id, String author);

}
