package br.com.obpc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.entities.Comment;
import br.com.obpc.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository repository;
	
	
	public Optional<Comment> saveComment(Comment newComment){
		return Optional.of(repository.save(newComment));
	}
	

	public List<Comment> getCommentByBookId(String bookId){		
		return repository.findCommentByBookId(bookId);		
	}
	
	public void deleteComment(String commentId) {
		repository.deleteById(commentId);
	}

}
