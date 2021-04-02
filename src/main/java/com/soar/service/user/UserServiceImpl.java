package com.soar.service.user;

import com.soar.dao.BaseDao;
import com.soar.dao.user.UserDao;
import com.soar.dao.user.UserDaoImpl;
import com.soar.pojo.User;

import java.sql.Connection;

public class UserServiceImpl implements UserService {
    UserDao userDao = null;

    //引入dao层
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    public boolean updatePwd(int id, String pwd) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection, id, pwd) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }


}
