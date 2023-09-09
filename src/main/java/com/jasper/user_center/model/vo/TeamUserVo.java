package com.jasper.user_center.model.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 队伍和用户信息封装类
 *
 * @author Jasper
 * @create 2023-08-30 15:10
 */
@Data
public class TeamUserVo implements Serializable {

    private static final long serialVersionUID = -3818404495644926101L;
    /**
     * 队伍Id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人用户信息
     */
    UserVo createUser;


    /**
     * 成员用户信息
     */
    List<UserVo> memberUser;

    /**
     * 成员用户数
     */
    Long userNum;

    /**
     * 用户是否已加入队伍
     */
    private boolean hasJoin = false;
}
