package com.event.model;

import com.event.ValidateData.ValidateUserData;

public class User {

    private int idUser;
    private String name;
    private String password;


    private String userName;
    private String type;

    public User(int id, String name, String userName, String password, String type) {
        this.idUser = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        boolean status = ValidateUserData.validateId(idUser);
        if (status)
            this.idUser = idUser;
        else
            System.out.println("Id is out of range");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        boolean status = ValidateUserData.validateName(name);
        if (status)
            this.name = name;
        else
            System.out.println("Name can not contain special characters or digits");
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}


