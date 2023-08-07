package com.jasper.user_center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jasper.user_center.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jasper
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-05-22 23:04:22
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   账号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String PlanetCode);

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 登出
     * @param request
     * @return
     */
    Integer userLogout(HttpServletRequest request);

    /**
     * 查询用户信息
     * @param name
     * @return
     */
    List<User> searchUser(String name);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList 用户要拥有的标签
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 用户修改信息
     *
     * @param user
     * @param request
     * @return
     */
    int updateUser(User user, User request);

    User getLoginUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User loginUser);
}
