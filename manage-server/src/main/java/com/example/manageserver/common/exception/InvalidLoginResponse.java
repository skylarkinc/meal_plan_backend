package com.example.manageserver.common.exception;

public class InvalidLoginResponse {
    private boolean success;
    private String token;
    private String username;
    private String password;


    public InvalidLoginResponse() {
        this.success = false;
        this.token = "null";
        this.username = "Invalid";
        this.password =   "Invalid" ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
