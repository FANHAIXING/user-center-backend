package com.jasper.user_center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jasper.user_center.model.domain.Team;
import com.jasper.user_center.model.domain.User;
import com.jasper.user_center.model.dto.TeamQuery;
import com.jasper.user_center.model.request.TeamJoinRequest;
import com.jasper.user_center.model.request.TeamQuitRequest;
import com.jasper.user_center.model.request.TeamUpdateRequest;
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
     * @param teamQuery, isAdmin
     * @return
     */
    List<TeamUserVo> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 队长解散队伍
     *
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
