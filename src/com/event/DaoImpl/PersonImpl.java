package com.event.DaoImpl;

import com.event.Config.Config;
import com.event.Dao.PersonDAO;
import com.event.model.Member;
import com.event.model.User;

import java.sql.*;
import java.util.ArrayList;

public class PersonImpl implements PersonDAO {

    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Connection connection;

    public PersonImpl() {
        connection = Config.createConnection();
    }

    @Override
    public boolean insertUser(User user) {


        String query = "insert into `User` (`name`, userName,`password`,`type`) values (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getType());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public User GetByUsernameAndPassword(String username, String password) {

        User result = null;
        int userIdDb;
        String userNameDb;
        String typeDb;
        String nameDb;
        String passwordDb;


        try {
            String sql = "SELECT `idUser`,`userName`,`type`,`name`,`password` FROM mydb.user WHERE `isActive`=1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                userIdDb = resultSet.getInt(1);
                userNameDb = resultSet.getString(2);
                typeDb = resultSet.getString(3);
                nameDb = resultSet.getString(4);
                passwordDb = resultSet.getString(5);

                if (userNameDb.compareTo(username) == 0 && passwordDb.compareTo(password) == 0) {
                    result = new User();

                    result.setIdUser(userIdDb);
                    result.setType(typeDb);
                    result.setName(nameDb);
                    result.setUserName(userNameDb);
                }

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }


        return result;

    }

    @Override
    public ArrayList<User> GetAllUsers() {

        ArrayList<User> userList = new ArrayList<>();
        try {
            String sql = "Select `idUser`,`userName`,`type`,`name` from `User` WHERE `isActive`=1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idUser = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String type = resultSet.getString(3);
                String name = resultSet.getString(4);
                User user = new User();
                user.setIdUser(idUser);
                user.setName(name);
                user.setUserName(userName);
                user.setType(type);


                userList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User GetByUserId(int idUser) {

        User user = new User();

        try {
            //`name`, userName,`password`,`type`
            String sql = "SELECT `idUser`,`userName`,`type`,`name`,`password` FROM mydb.user WHERE `idUser`=" + idUser;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setType(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return user;
    }

    @Override
    public void EditUser(User user) {

        String sql = "UPDATE mydb.user SET `userName`=?, `type`=?,`name`=?,`password`=? WHERE `idUser`=" + user.getIdUser();
        int rowsUpdated = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getType());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getPassword());
            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }


    }


    @Override
    public void DeleteUser(int idUser) {
        try {
//			String query = "delete from mydb.user where `idUser` =" + idUser;
            String query = "UPDATE mydb.user SET `isActive`=0 WHERE `idUser`=" + idUser;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("user deleted Successfully");
    }


    @Override
    public boolean isMember(int idUser) {

        try {
            String sql = "SELECT 1 FROM mydb.member WHERE `isActive`=1 AND `idMember`=" + idUser;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean isLead(int idUser) {

        try {
            String sql = "SELECT 1 FROM mydb.team WHERE `isActive`=1 AND lead=" + idUser;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<User> GetAllUsersForTeam() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            String sql = "Select `idUser`,`userName`,`type`,`name` from  mydb.user u left join mydb.team t ON u.idUser=t.lead WHERE u.isActive=1 AND t.lead IS NULL";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idUser = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String type = resultSet.getString(3);
                String name = resultSet.getString(4);
                User user = new User();
                user.setIdUser(idUser);
                user.setName(name);
                user.setUserName(userName);
                user.setType(type);


                userList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public ArrayList<Member> GetAllMembersByTeam(int idTeam) {
        ArrayList<Member> members = new ArrayList<>();
        try {
            String sql = "Select m.idMember,u.name,t.idTeam,t.teamName from mydb.member m left join mydb.team t ON m.teamId=t.idTeam \n" +
                    "left join mydb.user u ON m.idMember=u.idUser \n" +
                    "where m.isActive=1 AND t.idTeam=" + idTeam;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Member member = new Member();
                member.setIdMember(resultSet.getInt(1));
                member.setNameMember(resultSet.getString(2));
                member.setTeamId(resultSet.getInt(3));
                member.setTeamName(resultSet.getString(4));

                members.add(member);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


}

	

	

