package com.event.Dao;

import com.event.model.Member;
import com.event.model.Team;
import com.event.model.User;

import java.util.ArrayList;

public interface MemberDAO {


    //	methods for lead
    boolean insertMember(int idTeam, int idMember);

    ArrayList<Member> GetAllTeamMembers(int teamId);

    User GetByMemberId(int idMember);

    void DeleteTeamMembers(int idTeam, int idMember);

    void EditTeam(Team team);

    int getTeamIdByUser(int idUser);

    ArrayList<User> getAvailableUsers();

}
