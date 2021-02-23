package com.artesaniasclient.interfaces;

import com.artesaniasclient.model.User;

import java.util.ArrayList;

public interface IUserComunication {

    void login(User user);
    void get_users(ArrayList<User> users);
}
