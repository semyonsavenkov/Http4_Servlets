package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import main.java.ru.netology.exception.NotFoundException;

// Stub
@Repository
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> mapOfPosts = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public ConcurrentHashMap<Long, Post> all() {
        return mapOfPosts;
    }

    public Optional<Post> getById(long id) {
        return Optional.of(mapOfPosts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(id.addAndGet(1));
            mapOfPosts.put(post.getId(), post);
            return post;
        }
        if (mapOfPosts.containsKey(post.getId())) {
            mapOfPosts.get(post.getId()).setContent(post.getContent());
        } else {
            throw new NotFoundException();
        }
        return post;
    }

    public void removeById(long id) {
        mapOfPosts.remove(id);
    }
}
