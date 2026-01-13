package com.ntg.JobHunter.service;

import com.ntg.JobHunter.domain.res.CreatedUserResponse;
import com.ntg.JobHunter.domain.res.ResultPaginationResponse;
import com.ntg.JobHunter.domain.res.UpdatedUserResponse;
import com.ntg.JobHunter.domain.User;
import com.ntg.JobHunter.domain.dto.UpdateUserDTO;
import com.ntg.JobHunter.repository.UserRepository;
import com.ntg.JobHunter.utils.convert.UserConvert;
import com.ntg.JobHunter.utils.exception.IdInvalidException;
import com.ntg.JobHunter.utils.response.FormatResultPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreatedUserResponse createUser(User user) throws Exception {
        String email = user.getEmail();

        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }


        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return UserConvert.convertToResCreatedUserRes(this.userRepository.save(user));
    }

    public CreatedUserResponse fetchUserById(Long id) throws Exception {
        if(userRepository.existsById(id)){
            return UserConvert.convertToResCreatedUserRes(this.userRepository.findById(id).get());
        }else{
            throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public void deleteUser(Long id) throws Exception {
        if(this.userRepository.existsById(id)){
            this.userRepository.deleteById(id);
        }else{
            throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public ResultPaginationResponse getAllUser(Pageable pageable, Specification<User> spec){
        Page<User> userPage = this.userRepository.findAll(spec, pageable);

        return FormatResultPagination.createPaginateUserRes(userPage);
    }

    public UpdatedUserResponse updateUser(Long id, UpdateUserDTO user) throws Exception {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            User currentUser = userOptional.get();
            currentUser.setName(user.getName());
            currentUser.setGender(user.getGender());
            currentUser.setAge(user.getAge());
            currentUser.setAddress(user.getAddress());



            return UserConvert.convertToResUpdatedUserRes(this.userRepository.save(currentUser));
        }
        return null;
    }

    public void updateUserToken(String token, String email){
        User user = this.handleGetUserByUsername(email);
        if(user != null){
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email){
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }
}
