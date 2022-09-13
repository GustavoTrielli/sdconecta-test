package com.sdconecta.backendtest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdconecta.backendtest.dtos.UserDto;
import com.sdconecta.backendtest.dtos.UserUpdateDto;
import com.sdconecta.backendtest.models.UserModel;
import com.sdconecta.backendtest.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email is already in use.");
        }

        // convert UserDto to User entity
        UserModel userRequest = modelMapper.map(userDto, UserModel.class);
        UserModel savedUser = userService.save(userRequest);

        // convert User entity to UserDto class
        UserDto userResponse = modelMapper.map(savedUser, UserDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam("name") Optional<String> name,
            @RequestParam("specialty") Optional<String> specialty) {
        
        List<UserDto> userResponse = userService.findAll(name, specialty).stream().map(user -> {
            return modelMapper.map(user, UserDto.class);
        }).collect(Collectors.toList());;
            

            
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        UserDto userResponse = modelMapper.map(userModelOptional.get(), UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateOneUser(@PathVariable(value = "id") int id,
            @RequestBody @Valid UserUpdateDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        UserModel userModel = userModelOptional.get();
        if (userModel.getEmail() != userDto.getEmail() && userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email is already in use.");
        }

        // convert UserDto to User entity
        UserModel userRequest = modelMapper.map(userDto, UserModel.class);
        userRequest.setId(userModel.getId());

        if (userRequest.getEmail() == null) {
            userRequest.setEmail(userModel.getEmail());
        }
        if (userRequest.getMobilePhone() == null) {
            userRequest.setMobilePhone(userModel.getMobilePhone());
        }
        if (userRequest.getName() == null) {
            userRequest.setName(userModel.getName());
        }
        if (userRequest.getSurname() == null) {
            userRequest.setSurname(userModel.getSurname());
        }
        if (userRequest.getPassword() == null) {
            userRequest.setPassword(userModel.getPassword());
        }
        if (userRequest.getCrms() == null) {
            userRequest.setCrms(userModel.getCrms());
        }
        UserModel savedUser = userService.save(userRequest);

        // convert User entity to UserDto class
        UserDto userResponse = modelMapper.map(savedUser, UserDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfully.");
    }

}
