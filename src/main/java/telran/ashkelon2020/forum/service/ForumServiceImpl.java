package telran.ashkelon2020.forum.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.forum.dao.PostRepository;
import telran.ashkelon2020.forum.dto.CommentDto;
import telran.ashkelon2020.forum.dto.DatePeriodDto;
import telran.ashkelon2020.forum.dto.NewCommentDto;
import telran.ashkelon2020.forum.dto.NewPostDto;
import telran.ashkelon2020.forum.dto.PostDto;
import telran.ashkelon2020.forum.dto.exceptions.PostNotFoundException;
import telran.ashkelon2020.forum.model.Comment;
import telran.ashkelon2020.forum.model.Post;

@Service
public class ForumServiceImpl implements ForumService {

	@Autowired
	PostRepository forumRepository;

	@Override
	public PostDto addNewPost(NewPostDto newPost, String author) {
		Post post = new Post(newPost.getTitle(), newPost.getContent(), author, newPost.getTags());
		post = forumRepository.save(post);
		return convertToPostDto(post);
	}

	@Override
	public PostDto getPost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return convertToPostDto(post);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.delete(post);
		return convertToPostDto(post);
	}

	@Override
	public PostDto updatePost(NewPostDto postUpdateDto, String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String content = postUpdateDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
		String title = postUpdateDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		Set<String> tags = postUpdateDto.getTags();
		if (tags != null) {
			tags.forEach(post::addTag);
		}
		forumRepository.save(post);
		return convertToPostDto(post);
	}

	@Override
	public boolean addLike(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addLike();
		forumRepository.save(post);
		return true;

	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addComment(convertToComment(author, newCommentDto.getMessage()));
		forumRepository.save(post);
		return convertToPostDto(post);
	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return forumRepository.findByAuthor(author)
				.map(this::convertToPostDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public Iterable<CommentDto> findAllPostComments(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Set<Comment> comments = post.getComments();
		return comments.stream()
				.map(this::convertToCommentDto)
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CommentDto> findAllPostCommentsByAuthor(String id, String author) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Stream<Comment> comments = post.getComments()
				.stream()
				.filter(p -> author.equals(p.getUser()));
		return comments
				.map(this::convertToCommentDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags)
				.map(this::convertToPostDto)
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByDates(DatePeriodDto datePeriodDto) {
		return forumRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
				.map(this::convertToPostDto)
				.collect(Collectors.toList());
	}

	private PostDto convertToPostDto(Post post) {
		return PostDto.builder()
				.id(post.getId())
				.author(post.getAuthor())
				.title(post.getTitle())
				.dateCreated(post.getDateCreated())
				.content(post.getContent())
				.tags(post.getTags())
				.likes(post.getLikes())
				.comments(post.getComments().stream().map(this::convertToCommentDto).collect(Collectors.toList()))
				.build();
	}

	private Comment convertToComment(String author, String message) {
		return new Comment(author, message);
	}

	private CommentDto convertToCommentDto(Comment comment) {
		return CommentDto.builder()
				.user(comment.getUser())
				.message(comment.getMessage())
				.dateCreated(comment.getDateCreated())
				.likes(comment.getLikes())
				.build();
	}

}
