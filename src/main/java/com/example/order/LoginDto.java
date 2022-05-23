package com.example.order;



public class LoginDto {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    protected LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto(String username, String password, String firstName, String lastName) {
       this(username, password);
       this.firstName = firstName;
       this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
