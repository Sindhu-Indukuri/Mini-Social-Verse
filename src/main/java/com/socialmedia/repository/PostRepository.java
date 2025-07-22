
package com.socialmedia.repository;

import com.socialmedia.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    void deleteById(Integer id);
    List<Post> findByUserId(Integer user);

}
