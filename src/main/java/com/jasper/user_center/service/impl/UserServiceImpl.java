package com.jasper.user_center.service.impl;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jasper.user_center.common.ErrorCode;
import com.jasper.user_center.common.PageRequest;
import com.jasper.user_center.constant.UserConstant;
import com.jasper.user_center.exception.BusinessException;
import com.jasper.user_center.mapper.UserMapper;
import com.jasper.user_center.model.domain.User;
import com.jasper.user_center.model.vo.UserVo;
import com.jasper.user_center.service.UserService;
import com.jasper.user_center.utils.AlgorithmUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.yaml.snakeyaml.events.Event;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jasper.user_center.constant.UserConstant.ADMIN_ROLE;
import static com.jasper.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Jasper
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-05-22 23:04:22
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     */
    private static final String SALT = "jasper";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1、校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码过短");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "编号过长");
        }

        // 账户不包含特殊字符
        String validPattern = "^\\w+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码不符合要求");
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码与校验密码不同");
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户已存在");
        }

        // 编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "编号已存在");
        }

        //2.加密
        String encriptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encriptPassword);
        user.setPlanetCode(planetCode);

        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1、校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号密码输入为空！");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户账号必须大于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码必须大于8位");
        }
        // 账户不包含特殊字符
        String validPattern = "^\\w+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号不包含特殊字符");
        }

        //2.加密
        String encriptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encriptPassword);
        User user = userMapper.selectOne(queryWrapper);

        //用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户不存在或密码错误");
        }
        //3.用户脱敏
        User safetyUser = getSafetyUser(user);

        //4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUseStatus(originUser.getUseStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setTags(originUser.getTags());
        return safetyUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public List<User> searchUser(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("username", name);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    /**
     * 根据标签搜索用户（内存过滤）
     *
     * @param tagNameList 用户要拥有的标签
     * @return
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //查询所有用户
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        //内存中判断是否包含要求标签
        return userList.stream().filter(user -> {
            String tagStr = user.getTags();
            if (StringUtils.isBlank(tagStr)) {
                return false;
            }
            Set<String> tempTagNameSet = gson.fromJson(tagStr, new TypeToken<Set<String>>() {
            }.getType());
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
            for (String tagName : tagNameList) {
                if (!tagStr.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public int updateUser(User user, User loginUser) {
        long userId = loginUser.getId();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        //如果是管理员，允许更新任意用户
        if (isAdmin(loginUser)) {
            User oldUser = userMapper.selectById(userId);
            if (oldUser == null) {
                throw new BusinessException(ErrorCode.NULL_ERROR);
            }
            return userMapper.updateById(user);
        }
        //如果是用户自己，允许更新自己
        if (!loginUser.getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        return userMapper.updateById(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return (User) userObj;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        //鉴权
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;
    }

    /**
     * 匹配用户列表
     *
     * @param num
     * @param loginUser
     * @return
     */
    @Override
    public List<UserVo> matchUsers(@RequestBody long num, User loginUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("tags");
        queryWrapper.select("id", "tags");
        List<User> userList = this.list(queryWrapper);
        String tags = loginUser.getTags();
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());
        //用户列表的下标 => 相似度
        List<Pair<User, Long>> list = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String userTags = user.getTags();
            if (StringUtils.isBlank(userTags) || Objects.equals(loginUser.getId(), user.getId())) {
                continue;
            }
            List<String> userTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
            }.getType());
            //计算分数
            long distance = AlgorithmUtils.minDistance(tagList, userTagList);
            list.add(Pair.of(user, distance));
        }
        List<Pair<User, Long>> topUserIdList = list.stream().sorted((a, b)->(int)(a.getSecond()-b.getSecond())).limit(num).collect(Collectors.toList());
        List<UserVo> userVolist = new ArrayList<>();
        for (Pair<User, Long> Pair : topUserIdList) {
            userVolist.add(this.getUserVO(this.getById(Pair.getFirst())));
        }
        return userVolist;
    }

    /**
     * 根据标签查用户（SQL）
     *
     * @param tagNameList 用户标签列表
     * @return List<User>
     */
    @Deprecated
    public List<User> searchUsersByTagsBySQL(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //拼接and查询
        for (String tagName : tagNameList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);

        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public UserVo getUserVO(User user) {
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setUserAccount(user.getUserAccount());
        userVo.setUserRole(user.getUserRole());
        userVo.setId(user.getId());
        userVo.setEmail(user.getEmail());
        userVo.setGender(user.getGender());
        userVo.setPhone(user.getPhone());
        userVo.setAvatarUrl(user.getAvatarUrl());
        userVo.setTags(user.getTags());
        userVo.setProfile(user.getProfile());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setPlanetCode(user.getPlanetCode());
        userVo.setUseStatus(user.getUseStatus());
        userVo.setUpdateTime(user.getUpdateTime());
        return userVo;
    }
}




