package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    // create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId) throws IOException;

    //get all user
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List <UserDto> searchUser(String keyword);

    Optional<User> findUserByEmailOptional(String email);

    //other user specific features

}
