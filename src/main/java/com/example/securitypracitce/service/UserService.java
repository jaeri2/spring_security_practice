package com.example.securitypracitce.service;

import com.example.securitypracitce.domain.dto.UserDto;
import com.example.securitypracitce.domain.entity.Authority;
import com.example.securitypracitce.domain.entity.RoleType;
import com.example.securitypracitce.domain.entity.User;
import com.example.securitypracitce.exception.DuplicateMemberException;
import com.example.securitypracitce.repository.AuthorityRepository;
import com.example.securitypracitce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(UserDto userDto) {
        if(userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null){
            throw new DuplicateMemberException("이미 가입되어있는 유저입니다.");
        }

        Authority authority;
        if (authorityRepository.findById(RoleType.ROLE_USER).orElse(null) == null) {
            authority = Authority.builder()
                    .authorityName(RoleType.ROLE_USER)
                    .build();
            authorityRepository.save(authority);
        } else {
            authority = authorityRepository.findById(RoleType.ROLE_USER).get();
        }


        User newUser = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(newUser);

        return UserDto.from(newUser);
    }

    public List<UserDto> findAllMember() {
        List<User> allUser = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        for (User user : allUser) {
            result.add(UserDto.from(user));
        }

        return result;
    }

}
