package com.event.DaoImpl;

import com.event.Config.Config;
import com.event.Dao.TeamDAO;
import com.event.model.Team;

import java.sql.*;
import java.util.ArrayList;

public class TeamImpl implements TeamDAO {

    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Connection connection;

    public TeamImpl() {
        connection = Config.createConnection();
    }


    @Override
    public boolean insertTeam(Team team) {

        String query = "insert into `team` (`teamName`, `lead`) values (?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, team.getTeamName());
            preparedStatement.setInt(2, team.getTeamLead());


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList<Team> GetAllTeams() {

        ArrayList<Team> teamlist = new ArrayList<>();
        try {
            String sql = "SELECT idTeam,teamName,lead,u.name FROM mydb.team t left join mydb.user u ON t.lead=u.idUser WHERE  t.isActive=1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Team team = new Team();
                team.setIdTeam(resultSet.getInt(1));
                team.setTeamName(resultSet.getString(2));
                team.setTeamLead(resultSet.getInt(3));
                team.setTeamLeadName(resultSet.getString(4));


                teamlist.add(team);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamlist;
    }

    @Override
    public Team GetByTeamId(int idTeam) {
        Team team = new Team();

        try {
            //SELECT idTeam,teamName,lead,u.name as leadName FROM mydb.team t left join mydb.user u ON t.lead=u.idUser  WHERE t.idTeam=
            //SELECT `idTeam`,`teamName`,`lead` FROM mydb.team WHERE `idTeam`=
            String sql = "SELECT idTeam,teamName,lead,u.name as leadName FROM mydb.team t left join mydb.user u ON t.lead=u.idUser  WHERE t.idTeam=" + idTeam;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                team.setIdTeam(resultSet.getInt(1));
                team.setTeamName(resultSet.getString(2));
                team.setTeamLead(resultSet.getInt(3));
                team.setTeamLeadName(resultSet.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return team;
    }

    @Override
    public void EditTeam(Team team) {

        String sql = "UPDATE mydb.team SET `teamName`=?, `lead`=? WHERE `idTeam`=" + team.getIdTeam();
        int rowsUpdated = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, team.getTeamName());
            preparedStatement.setInt(2, team.getTeamLead());

            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            System.out.println("An existing team was updated successfully!");
        }


    }

    @Override
    public void DeleteTeam(int idTeam) {
        try {
            //UPDATE mydb.user SET `isActive`=0
            String query = "UPDATE mydb.team SET `isActive`=0 where `idTeam` =" + idTeam;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getTeamIdByUser(int idUser) {

        try {
            String sql = "SELECT idTeam  FROM mydb.team  WHERE lead=" + idUser;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

	

	

