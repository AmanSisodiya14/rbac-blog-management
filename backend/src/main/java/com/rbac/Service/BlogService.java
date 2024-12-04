package com.rbac.Service;

import com.rbac.Entity.Blog;
import com.rbac.Repository.BLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogService {

    @Autowired
    private BLogRepository bLogRepository;

    /**
     * Save a blog to the database.
     *
     * @param blog the blog to save
     */
    public void saveBlog(Blog blog) {
        log.info("Starting save process for blog: {}", blog);
        try {
            bLogRepository.save(blog);
            log.info("Blog saved successfully: {}", blog);
        } catch (Exception e) {
            log.error("Error occurred while saving blog: {}", blog, e);
            throw new RuntimeException("Failed to save blog", e);
        }
        log.info("Completed save process for blog: {}", blog);
    }
    /**
     * Deletes a blog from the database.
     *
     * @param id the id of the blog to delete
     */
    public void deleteBlog(Integer id) throws Exception {
        log.info("Deleting blog with id: {}", id);
        try {
            bLogRepository.deleteById(id);
            log.info("Blog deleted successfully");
        } catch (Exception e) {
            log.error("Exception occurred while trying to delete blog with id: {}", id, e);
            throw new Exception(e.getMessage());
        }
    }
    /**
     * Updates a blog in the database.
     *
     * @param blog the blog to update
     */
    public void updateBlog(Blog blog) {
        log.info("Starting update process for blog: {}", blog);
        try {
            bLogRepository.save(blog);
            log.info("Blog updated successfully: {}", blog);
        } catch (Exception e) {
            log.error("Error occurred while updating blog: {}", blog, e);
            throw new RuntimeException("Failed to update blog", e);
        }
        log.info("Completed update process for blog: {}", blog);
    }
    /**
     * Returns a list of all blogs in the database.
     *
     * @return the list of blogs
     */
    public List<Blog> getAllBlogs() {
        log.info("Retrieving all blogs from the database");
        log.info("Entering BlogService.getAllBlogs()");
        List<Blog> blogs = null;
        try {
            blogs = bLogRepository.findAll();
            log.info("Retrieved all blogs from the database: {}", blogs);
        } catch (Exception e) {
            log.error("Error occurred while retrieving all blogs: {}", e.getMessage(), e);
        }
        log.trace("Exiting BlogService.getAllBlogs()");
        return blogs;
    }
    /**
     * Returns a blog by its id.
     *
     * @param id the id of the blog to retrieve
     * @return the blog with the given id
     * @throws RuntimeException if the blog is not found
     */
    public Blog getBlogs(Integer id) {
        log.info("Retrieving blog with id: {}", id);
        Blog blog = bLogRepository.findById(id).orElseThrow(() -> new RuntimeException("blog not found"));
        log.info("Retrieved blog: {}", blog);
        return blog;
    }
}
