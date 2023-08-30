package com.jasper.user_center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jasper.user_center.model.domain.Team;
import com.jasper.user_center.model.domain.User;
import com.jasper.user_center.model.dto.TeamQuery;
import com.jasper.user_center.model.vo.TeamUserVo;

import java.util.List;

/**
* @author Jasper
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-08-17 15:44:25
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    Long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @param loginUser
     * @return
     */
    List<TeamUserVo> listTeams(TeamQuery teamQuery,boolean isAdmin);
}
