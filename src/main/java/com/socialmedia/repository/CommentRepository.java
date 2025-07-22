
package com.socialmedia.repository;

import com.socialmedia.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
	 List<Comment> findByPostId(Integer postId);
}
