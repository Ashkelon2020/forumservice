package telran.ashkelon2020.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.forum.dto.CommentDto;
import telran.ashkelon2020.forum.dto.DatePeriodDto;
import telran.ashkelon2020.forum.dto.NewCommentDto;
import telran.ashkelon2020.forum.dto.NewPostDto;
import telran.ashkelon2020.forum.dto.PostDto;
import telran.ashkelon2020.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {
	@Autowired
	ForumService service;

	@PostMapping("/post/{author}")
	public PostDto addPost(@RequestBody NewPostDto newPost, @PathVariable String author) {
		return service.addNewPost(newPost, author);
	}

	@GetMapping("/post/{id}")
	public PostDto getPost(@PathVariable String id) {
		return service.getPost(id);
	}

	@DeleteMapping("/post/{id}")
	public PostDto removePost(@PathVariable String id) {
		return service.removePost(id);
	}

	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto postUpdateDto) {
		return service.updatePost(postUpdateDto, id);
	}

	@PutMapping("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return service.addLike(id);
	}

	@PutMapping("/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String id, @PathVariable String author, @RequestBody NewCommentDto newCommentDto) {
		return service.addComment(id, author, newCommentDto);
	}

	@GetMapping("/posts/author/{author}")
	public Iterable<PostDto> getPostsByAuthor(@PathVariable String author) {
		return service.findPostsByAuthor(author);
	}
	
	@PostMapping("/posts/tags")
	public Iterable<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return service.findPostsByTags(tags);
	}
	
	@PostMapping("/posts/period")
	public Iterable<PostDto> findPostsByDate(@RequestBody DatePeriodDto datePeriodDto) {
		return service.findPostsByDates(datePeriodDto);
	}
	
	@GetMapping("/post/{id}/comments")
	public Iterable<CommentDto> findAllPostComments(@PathVariable String id){
		return service.findAllPostComments(id);
	}
	
	@GetMapping("/post/{id}/author/{author}/comments")
	public Iterable<CommentDto> findAllPostCommentsByAuthor(@PathVariable String id, @PathVariable String author) {
		return service.findAllPostCommentsByAuthor(id, author);
	}

}
