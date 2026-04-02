package com.engineer.batchprocessing.service.Impl;

import com.engineer.batchprocessing.dto.UseSearchrRequest;
import com.engineer.batchprocessing.entity.User;
import com.engineer.batchprocessing.repository.UserRepository;
import com.engineer.batchprocessing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> fatchAllUsers (int pageNo, int pageSize, String sortBy, String direction, String search) {
        Sort sort = direction.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest request = PageRequest.of (pageNo, pageSize, sort);

        if ( !StringUtils.hasText (search) )
            return userRepository.findAll (request).getContent ();
        else
            return userRepository.findByUserName (search, request).getContent ();
    }

    @Override
    public
    List<User> getAllUsers (UseSearchrRequest request) {
        try {
            Sort sort = request.getDirection ().equalsIgnoreCase("DESC")
                    ? Sort.by(request.getSortBy ()).descending()
                    : Sort.by(request.getSortBy ()).ascending();
            PageRequest pageRequest = PageRequest.of (request.getPageNo ()-1, request.getPageSize (), sort);

            if ( !StringUtils.hasText (request.getSearch ()) )
                return userRepository.findAll (pageRequest).getContent ();
            else
                return userRepository.findByUserName (request.getSearch (), pageRequest).getContent ();
        } catch (Exception e) {

        }
        return null;
    }

}
