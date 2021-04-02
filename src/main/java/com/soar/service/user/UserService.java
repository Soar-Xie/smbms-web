package com.soar.service.user;

import com.soar.pojo.User;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //根据用户id，修改密码
    public boolean updatePwd(int id, String pwd);
}
