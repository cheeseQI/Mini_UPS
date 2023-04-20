package model;

public class User {
    private Integer userId;
    private String password;

    public User(){}
    public User(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
