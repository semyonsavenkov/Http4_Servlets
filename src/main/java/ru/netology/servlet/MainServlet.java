package ru.netology.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;

public class MainServlet extends HttpServlet {
  private PostController controller;

  public static final String APPLICATION_JSON = "application/json";
  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String POSTS_PATH = "/api/posts";
  private static final String POST_PATH = "/api/posts/\\d+";
  private static final String DELIMITER = "/";

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
   this.controller = (PostController) context.getBean("postController");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {

    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(POSTS_PATH)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(POST_PATH)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(DELIMITER)));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST) && path.equals(POSTS_PATH)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(POST_PATH)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf(DELIMITER)));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

