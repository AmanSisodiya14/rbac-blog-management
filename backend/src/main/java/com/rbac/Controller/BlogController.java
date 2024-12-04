package com.rbac.Controller;

import com.rbac.Entity.Blog;
import com.rbac.Entity.User;
import com.rbac.Service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * This endpoint is only accessible to users with the 'ADMIN' role.
     * The endpoint takes a Blog object as a request body and creates a new blog in the database.
     * The blog's author is set to the user who made the request.
     * @param blog The blog to be created
     * @param request The HttpServletRequest object
     * @return A ResponseEntity object with a status code of 200 and a message saying "Save blog!"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/blog")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpServletRequest request) {
        // Get the user ID from the request
        Integer userId = (Integer) request.getAttribute("userId");
        // Set the blog's author to the user
        blog.setAuthor(new User(userId));
        // Save the blog to the database
        blogService.saveBlog(blog);
        // Return a ResponseEntity object with a status code of 200 and a message
        return ResponseEntity.ok("Save blog!");
    }

    /**
     * This endpoint is accessible to users with either the 'ADMIN' or 'USER' role.
     * The endpoint returns a list of all blogs in the database.
     * @return A ResponseEntity object with a status code of 200 and a list of blogs
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/blog")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        // Get the list of all blogs from the database
        List<Blog> blogs = blogService.getAllBlogs();
        // Return a ResponseEntity object with a status code of 200 and the list of blogs
        return ResponseEntity.ok(blogs);
    }
    /**
     * This endpoint is accessible to users with the 'ADMIN' role.
     * The endpoint takes an id as a path variable and returns a blog with the given id.
     * @param id The id of the blog to retrieve
     * @return A ResponseEntity object with a status code of 200 and the blog with the given id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> getBlogs(@PathVariable Integer id) {
        // Get the blog with the given id from the database
        Blog blog = blogService.getBlogs(id);
        // Return a ResponseEntity object with a status code of 200 and the blog
        return ResponseEntity.ok(blog);
    }


    /**
     * This endpoint is accessible to users with the 'ADMIN' role.
     * It updates a blog with the given id using the provided blog details.
     * The blog's author is set to the user who made the request.
     *
     * @param id The id of the blog to update
     * @param blog The blog details to update
     * @param request The HttpServletRequest object
     * @return A ResponseEntity object with a status code of 200 and a message saying "Update Success!"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/blog/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody Blog blog, HttpServletRequest request) {
        // Set the blog's id to the provided id
        blog.setId(id);
        // Get the user ID from the request
        Integer userId = (Integer) request.getAttribute("userId");
        // Set the blog's author to the user
        blog.setAuthor(new User(userId));
        // Update the blog in the database
        blogService.updateBlog(blog);
        // Return a ResponseEntity object with a status code of 200 and a message
        return ResponseEntity.ok("Update Success!");
    }
    /**
     * This endpoint is accessible to users with the 'ADMIN' role.
     * It deletes a blog with the given id from the database.
     * @param id The id of the blog to delete
     * @return A ResponseEntity object with a status code of 200 and a message saying "Delete Success!"
     * @throws Exception If the blog is not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) throws Exception {
        // Delete the blog with the given id from the database
        blogService.deleteBlog(id);
        // Return a ResponseEntity object with a status code of 200 and a message
        return ResponseEntity.ok("Delete Success!");
    }
}
