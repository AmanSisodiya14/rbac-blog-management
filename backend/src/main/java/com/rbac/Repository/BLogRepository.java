package com.rbac.Repository;

import com.rbac.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BLogRepository extends JpaRepository<Blog,Integer> {

}
