package com.jasper.user_center.model.dto;

import com.jasper.user_center.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 队伍查询封装类
 * @author Jasper
 * @create 2023-08-17 23:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeamQuery extends PageRequest {
    private static final long serialVersionUID = 768702861294562082L;
    /**
     * id
     */
    private Long id;

    /**
     * 队伍Id列表
     */
    private List<Long> idList;

    /**
     * 搜索关键词（同事对队伍名称和描述搜索）
     */
    private String searchText;

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
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;
}
