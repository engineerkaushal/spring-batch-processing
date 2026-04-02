package com.engineer.batchprocessing.controller;

import com.engineer.batchprocessing.dto.UseSearchrRequest;
import com.engineer.batchprocessing.dto.UserRequestDTO;
import com.engineer.batchprocessing.entity.User;
import com.engineer.batchprocessing.repository.UserRepository;
import com.engineer.batchprocessing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok ( "User created successfully" );
    }

    @GetMapping("/allUser")
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "1", required = false) Integer pageNo,
                                        @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                        @RequestParam(defaultValue = "ASC", required = false) String direction,
                                        @RequestParam(defaultValue = "userId", required = false) String sortBy,
                                        @RequestParam(required = false) String search) {
        List<User> users = userService.fatchAllUsers ( pageNo-1, pageSize, sortBy, direction, search);
        return new ResponseEntity<> (users, HttpStatus.OK);
    }

    @PostMapping("/allUsers")
    public ResponseEntity<?> getAllUsers(@RequestBody UseSearchrRequest request) {
        List<User> users = userService.getAllUsers ( request);
        return new ResponseEntity<> (users, HttpStatus.OK);
    }
}
