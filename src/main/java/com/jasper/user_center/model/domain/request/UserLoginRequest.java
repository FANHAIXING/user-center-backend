package com.jasper.user_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fanhaiping
 * @project user-center2
 * @data 2023-05-31
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1553918174977230422L;

    private String userAccount;

    private String userPassword;
}

