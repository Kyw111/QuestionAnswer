package com.myproject.board.SiteUser;

import com.myproject.board.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserDto of(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto CreateUser(String username, String password, String email){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password)); // 비밀번호는 따로 암호화해서 저장할 수 있도록 _ SecurityConfig.java에 passwordEncoder를 bean으로 등록해놨음
        userRepository.save(userEntity);
        return of(userEntity);
    }

    public UserDto getUser(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return of(userEntity.get());
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }

    }
}
