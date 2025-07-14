/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author user
 */
public class UserBean {
    public String user_name;
    public String user_username;
    public String user_email;
    public String user_password;
    

    public UserBean(String user_name, String user_username, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_email = user_email;
        this.user_password = user_password;
        
    }
    
    public UserBean() {
        this.user_name = "";
        this.user_username = "";
        this.user_email = "";
        this.user_password = "";
            }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_password() {
        return user_password;
    }

  
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
