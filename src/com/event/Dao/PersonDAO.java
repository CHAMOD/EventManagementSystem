package com.event.Dao;

import com.event.model.Member;
import com.event.model.User;

import java.util.ArrayList;

public interface PersonDAO {


    boolean insertUser(User user);

    ArrayList<User> GetAllUsers();

    User GetByUsernameAndPassword(String username, String password);

    User GetByUserId(int idUser);

    void EditUser(User user1);

    void DeleteUser(int userId);

    boolean isMember(int idUser);

    boolean isLead(int idUser);

    ArrayList<User> GetAllUsersForTeam();

    ArrayList<Member> GetAllMembersByTeam(int idTeam);
}
