package com.tecxpert.myeduapplication.model;

import java.io.Serializable;

public class User implements Serializable {
    String name,email,board,class_name,password;

    public String getName() {
        return name;
    }

    public String getBoard() {
        return board;
    }

    public String getClass_name() {
        return class_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public  User(String name, String email, String password, String board, String class_name){
        this.board=board;
        this.class_name=class_name;
        this.email=email;
        this.name=name;
        this.password=password;
    }
}
