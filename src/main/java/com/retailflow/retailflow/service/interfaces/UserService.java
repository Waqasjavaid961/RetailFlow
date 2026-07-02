package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.UserCreateReq;
import com.retailflow.retailflow.dto.request.UserLoginReq;
import com.retailflow.retailflow.dto.response.UserCreateResp;

public interface UserService {
    UserCreateResp register(UserCreateReq req);
   // UserCreateResp login(UserLoginReq req);
}
