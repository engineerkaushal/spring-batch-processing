package com.engineer.batchprocessing.service;

import com.engineer.batchprocessing.dto.UseSearchrRequest;
import com.engineer.batchprocessing.entity.User;

import java.util.*;

public interface UserService {
    List<User> fatchAllUsers(int pageNo, int pageSize, String sortBy, String direction, String search);

    List<User> getAllUsers(UseSearchrRequest request);
}
