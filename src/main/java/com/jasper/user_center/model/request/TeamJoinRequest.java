package com.jasper.user_center.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jasper
 * @create 2023-09-03 17:13
 */
@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = -7689583177926670235L;
    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
