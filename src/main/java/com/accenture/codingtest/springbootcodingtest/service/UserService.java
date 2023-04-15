package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.UserEntity;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.user.UserDTO;
import com.accenture.codingtest.springbootcodingtest.model.user.UserListDTO;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //todo = add logs wherever needed

    @Autowired
    private UserRepository userRepository;

    public UserListDTO getAllUsers() throws Exception {
        UserListDTO userList = new UserListDTO();
        List<UserEntity> userEntity = userRepository.findAll();

        // todo - return 400 error with response body if userEntity is empty

        for (UserEntity u: userEntity ){
            UserDTO user = new UserDTO();
            user.setId(u.getId().toString());
            user.setPassword(u.getPassword());
            user.setUsername(u.getUsername());

            userList.getData().add(user);
        }

        return userList;
    }

    public UserDTO getUserByID(String userId) throws Exception {
        UserDTO user = new UserDTO();

        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(userId));
        if(userEntity.isEmpty()) {
            //todo- logging needs
            throw new Exception("user doesn't exist");
        } else {
            user.setUsername(userEntity.get().getUsername());
            user.setPassword(userEntity.get().getPassword());
            user.setId(userEntity.get().getId().toString());
        }
        return user;
    }

    public UserDTO getUserByUsername(String username) throws Exception {
        UserDTO user = new UserDTO();

        // todo - need to implement a custom method
        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(username));
        if(userEntity.isEmpty()) {
            //todo- logging needs
            throw new Exception("user doesn't exist");
        } else {
            user.setUsername(userEntity.get().getUsername());
            user.setPassword(userEntity.get().getPassword());
            user.setId(userEntity.get().getId().toString());
        }
        return user;
    }

    public SuccessDTO createUser(UserDTO user) throws Exception {

        // create userEntity from request
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        // no need to set id

        //todo = if user name already exists it returns an error then catch it and throw an error instead 500 internal server error

        UserEntity x = userRepository.save(userEntity);
        // take id from the response from repository
        userEntity.setId(x.getId());
        //todo- logging needs

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setId(userEntity.getId().toString());
        return successDTO;
    }

    public SuccessDTO updateUser(String userId, UserDTO user) throws Exception {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(userId));
        if(userEntity.isEmpty()) {
            //todo- logging needs
            throw new Exception("user doesn't exist");
        } else {
            // updating the user entity for the updated record
            userEntity.get().setUsername(user.getUsername());
            userEntity.get().setPassword(user.getPassword());
        }

        // update the user
        userRepository.save(userEntity.get());
        //todo- logging needs

        successDTO.setId(userEntity.get().getId().toString());
        return successDTO;
    }

    public SuccessDTO patchUser(String userId, UserDTO user) throws Exception {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(userId));
        if(userEntity.isEmpty()) {
            //todo- logging needs
            throw new Exception("user doesn't exist");
        } else {
            // updating the user entity for the updated record
            if(user.getUsername() != null){userEntity.get().setUsername(user.getUsername());}
            if(user.getPassword() != null){userEntity.get().setPassword(user.getPassword());}
        }

        // update the user
        userRepository.save(userEntity.get());
        //todo- logging needs

        successDTO.setId(userEntity.get().getId().toString());
        return successDTO;
    }

    public void deleteUser(String userId) throws Exception {
        if(!userRepository.existsById(UUID.fromString(userId))){
            throw new Exception("user does not exist");
        }
        userRepository.deleteById(UUID.fromString(userId));
        //todo- logging needs
    }

}
