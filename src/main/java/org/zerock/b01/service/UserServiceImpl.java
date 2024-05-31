package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.User;
import org.zerock.b01.dto.UserDTO;
import org.zerock.b01.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // 의존성 주입 / 필드 선언 / 객체를 생성하는 코드가 아님(인터페이스는 객체 생성이 불가능)

    private final ModelMapper modelMapper;
    // 의존성 주입 / 필드 선언

    @Override
    public void signUp(UserDTO userDTO) {

        // ID 중복 체크
         if (userRepository.existsById(userDTO.getId())) {
            throw new IllegalArgumentException("ID already exists.");
        }
        // IllegalArgumentException : 잘못된 argument가 메서드에 전달될 때 발생하는 예외 (UserServiceImpl -> UserDTO)
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }

    @Override
    public boolean signIn(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getId());
        if (user.isPresent() && user.get().getPassword().equals(userDTO.getPassword())) {
            return true; // 로그인 성공
        }
        /*
        else { throw new NullPointException("invalid ID or Password);

        Optional<T> 을 사용하였기때문에 NullPointException 처리는 필요없음.
        또한 UserController 의 logInPost 메소드에서도 예외처리 필요없음.

         */

        return false; // 로그인 실패
    }
}
