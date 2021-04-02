package com.project.love_data.dao;

import com.project.love_data.dto.UserDTO;

import java.util.*;

public interface UserDAO {
    List<UserDTO> selectUsers(UserDTO param) throws Exception;
}
