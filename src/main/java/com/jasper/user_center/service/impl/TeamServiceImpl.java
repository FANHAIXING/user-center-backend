package com.jasper.user_center.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasper.user_center.model.domain.Team;
import com.jasper.user_center.mapper.TeamMapper;
import com.jasper.user_center.service.TeamService;
import org.springframework.stereotype.Service;

/**
* @author Jasper
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2023-08-17 15:44:25
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




