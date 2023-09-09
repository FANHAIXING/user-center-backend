package com.jasper.user_center.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求
 * @author Jasper
 * @create 2023-09-06 11:19
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = -6629787169635223960L;

    /**
     * 队伍Id
     */
    private long id;
}
