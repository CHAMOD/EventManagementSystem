package com.event.Dao;

import com.event.model.Team;

import java.util.ArrayList;

public interface TeamDAO {


    //	added by champ
    boolean insertTeam(Team team);

    ArrayList<Team> GetAllTeams();

    Team GetByTeamId(int idTeam);

    void EditTeam(Team team);

    void DeleteTeam(int idTeam);

    int getTeamIdByUser(int idUser);

}
