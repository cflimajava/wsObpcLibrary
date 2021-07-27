package br.com.obpc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.obpc.entities.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
	
	public List<Comment> findCommentByBookId(String bookId);
	
	
}
