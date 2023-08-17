package com.jasper.user_center.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用分页请求参数
 *
 * @author Jasper
 * @create 2023-08-18 0:13
 */
@Data
public class PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1424715155126071542L;

    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 页号
     */
    protected int pageNum = 1;
}
