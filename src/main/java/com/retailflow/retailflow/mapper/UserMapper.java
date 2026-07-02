package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.request.UserCreateReq;
import com.retailflow.retailflow.dto.response.UserCreateResp;
import com.retailflow.retailflow.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateReq req);
    UserCreateResp fromEntity(User user);

}
