package com.ntg.JobHunter.utils.convert;

import com.ntg.JobHunter.domain.res.CreatedUserResponse;
import com.ntg.JobHunter.domain.res.UpdatedUserResponse;
import com.ntg.JobHunter.domain.User;


// Function to convert User -> CreatedUserResponse, UpdateUserResponse
public class UserConvert {
    public static CreatedUserResponse convertToResCreatedUserRes(User user){
        CreatedUserResponse res = new CreatedUserResponse();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setCreatedDate(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setName(user.getName());
        return res;
    }

    public static UpdatedUserResponse convertToResUpdatedUserRes(User user){
        UpdatedUserResponse res = new UpdatedUserResponse();
        res.setId(user.getId());
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setName(user.getName());
        res.setLastModifiedDate(user.getUpdatedAt());
        return res;
    }
}
