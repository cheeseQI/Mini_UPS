package model;

public class User {
    private Integer userId;
    private String password;
    private String userName;

    public User(){}
    public User(Integer userId, String password, String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username= " + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
