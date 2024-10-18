package com.jinan.entities;

public class Login {
    int id;
    String username;
    String passwd;
    String name;
    String phone;
    String note;

    public Login(int id, String username, String passwd, String name, String phone, String note) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.name = name;
        this.phone = phone;
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
