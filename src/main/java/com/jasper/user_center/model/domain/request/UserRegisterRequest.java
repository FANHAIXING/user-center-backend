package com.jasper.user_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author fanhaiping
 * @project user-center2
 * @data 2023-05-31
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 4331082726969143457L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
