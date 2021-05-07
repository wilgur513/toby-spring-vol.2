package chapter4.controller;

import java.util.Objects;

public class User {
    private String id;
    private String pwd;
    private Integer level;

    public User(String id, String pwd, Integer level) {
        this.id = id;
        this.pwd = pwd;
        this.level = level;
    }

    public User() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(pwd, user.pwd) && Objects.equals(level, user.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pwd, level);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", level=" + level +
                '}';
    }

    public static User of(String id, String pwd , Integer level) {
        return new User(id, pwd, level);
    }
}
