package org.chat.application.dao;

/**
 * @author Saransh Gupta
 */
public class User {

    private String Id;
    private String userName;

    User(String Id, String userName)
    {
        this.Id = Id;
        this.userName = userName;

    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return Id;
    }
}
