package com.jasper.user_center.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasper.user_center.model.domain.UserTeam;
import com.jasper.user_center.mapper.UserTeamMapper;
import com.jasper.user_center.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author Jasper
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2023-08-17 15:43:29
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




