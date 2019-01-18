package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaSpecificationExecutor<User>,PagingAndSortingRepository<User,Long> {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
