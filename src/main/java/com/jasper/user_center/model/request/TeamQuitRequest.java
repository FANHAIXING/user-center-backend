package com.jasper.user_center.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户退出队伍请求体
 * @author Jasper
 * @create 2023-09-04 9:16
 */

@Data
public class TeamQuitRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6135474264446947231L;

    /**
     * id
     */
    private Long teamId;

}
