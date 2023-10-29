package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Stub
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
    post.setId(id.addAndGet(1));
    mapOfPosts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    mapOfPosts.remove(id);
  }
}
