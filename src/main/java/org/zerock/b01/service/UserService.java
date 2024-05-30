package org.zerock.b01.service;

import org.zerock.b01.dto.UserDTO;

public interface UserService {

    void signUp(UserDTO userDTO);
    boolean signIn(UserDTO userDTO);

}
