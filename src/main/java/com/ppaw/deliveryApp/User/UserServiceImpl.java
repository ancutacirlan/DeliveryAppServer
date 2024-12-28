package com.ppaw.deliveryApp.User;


import com.ppaw.deliveryApp.Exceptions.NotFoundException;
import com.ppaw.deliveryApp.Utils.Role;
import com.ppaw.deliveryApp.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository repo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder){ //no need for one constructor to have @Autowired annotation.
        this.repo=userRepository;
        this.passwordEncoder = passwordEncoder;
    }


//    @Override
//    public Users saveUser(Users user) {
//        return repo.save(user);
//    }


    @Override
    public List<Users> getAllUsers() {
        return repo.findAll();
    }


    @Override
    public Users getUserById(UUID id) {
        Optional<Users> opt = repo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NotFoundException("User with Id : " + id + " Not Found");
        }
    }


    @Override
    public void deleteUserById(UUID id) {
        repo.delete(getUserById(id));
    }

    @Override
    public void updateUser(Users user) {
        repo.save(user);
    }

    @Override
    public List<Users> getAllRestaurants() {
        return repo.findAllByRole(Role.RESTAURANT_ADMIN);
    }

    @Override
    public Users findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public ResponseEntity saveUser(UserDto userDto) {
        Users user = new Users();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());  //conversion of form data to jpa entity, here mapper won't work as userDto don't have common attributes with User.
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // before setting the password we are encrypting using Bcrypt by Spring security.
        user.setAddress("address");
        //userRepository.save(user);

        //Role role = repo.findByName("ROLE_ADMIN");

//        if(role==null){
//            role=checkRoleExists();
//        }
        user.setRole(Role.CLIENT);   //As we have list of roles field in user checking any role in db exists or not if not creating by a private function and saving it in db
        repo.save(user); // now saving user to db.

        return null;
    }


    @Override
    public List<UserDto> findAllUsers() {
        List<Users> users = repo.findAll();
        List<UserDto> user_dto = new ArrayList<>();
        for(Users user:users){
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            user_dto.add(userDto);
        }
        return user_dto;
    }

}