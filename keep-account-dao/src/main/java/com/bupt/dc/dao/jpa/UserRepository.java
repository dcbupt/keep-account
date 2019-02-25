package com.bupt.dc.dao.jpa;

import com.bupt.dc.object.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //User findByName(String name);
    //
    //User findByNameAndAge(String name, Integer age);
    //
    //@Query("from User u where u.name=:name")
    //User findUser(@Param("name") String name);

    User findByUsername(String username);
}
