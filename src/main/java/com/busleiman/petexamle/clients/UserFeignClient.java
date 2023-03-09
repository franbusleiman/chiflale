package com.busleiman.petexamle.clients;

import com.busleiman.petexamle.model.user.User;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "users-service")
public interface UserFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/username/{username}")
    ResponseEntity<User> findUserByUsername(@PathVariable("username") String username);
}
