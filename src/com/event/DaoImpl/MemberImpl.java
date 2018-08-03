package com.event.DaoImpl;

import com.event.Config.Config;
import com.event.Dao.MemberDAO;
import com.event.model.Member;
import com.event.model.Team;
import com.event.model.User;

import java.sql.*;
import java.util.ArrayList;

public class MemberImpl implements MemberDAO {

    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Connection connection;

    public MemberImpl() {
        connection = Config.createConnection();
    }


    @Override
    public boolean insertMember(int idTeam, int idMember) {
        String query = "insert into `member` (`idMember`, `teamId`) values (?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idMember);
            preparedStatement.setInt(2, idTeam);


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public ArrayList<Member> GetAllTeamMembers(int teamId) {

        ArrayList<Member> members = new ArrayList<>();
        try {
            String sql = "SELECT u.idUser,u.name FROM mydb.member m left join mydb.user u ON m.idMember=u.idUser WHERE  m.isActive=1 AND  m.teamId=" + teamId;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Member member = new Member();
                member.setIdMember(resultSet.getInt(1));
                member.setNameMember(resultSet.getString(2));
                members.add(member);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    @Override
    public User GetByMemberId(int idMember) {
        User user = new User();

        try {

            String sql = "SELECT `idUser`,`userName`,`name` FROM mydb.user u left join mydb.member m\n" +
                    "ON u.idUser=m.idMember WHERE `idMember`=" + idMember;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setName(resultSet.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return user;
    }

    @Override
    public void EditTeam(Team team) {

    }


    @Override
    public int getTeamIdByUser(int idUser) {
        return 0;
    }

    @Override
    public ArrayList<User> getAvailableUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            String sql = "SELECT *\n" +
                    "FROM mydb.user u\n" +
                    "WHERE NOT EXISTS (select null from mydb.team t where u.idUser = t.lead)\n" +
                    "AND NOT EXISTS (select null from mydb.member m where u.idUser = m.idMember)\n" +
                    "AND u.type<>\"admin\" AND u.isActive=1; ";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                User user = new User();
                user.setIdUser(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setName(resultSet.getString(5));
                userList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }


    @Override
    public void DeleteTeamMembers(int idTeam, int idMember) {

        try {
            String query = "UPDATE mydb.member SET `isActive`=0 where `teamId` =" + idTeam + " AND `idMember` =" + idMember;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

	

	

