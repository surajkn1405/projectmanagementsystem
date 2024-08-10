package com.suraj.service;

import com.suraj.modal.User;

public interface UserService {

    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserByEmail(String email)throws Exception;

    User findUserById(Long userId)throws Exception;

    User updateUserProjectSize(User user, Integer number)throws Exception;
}
