package hibernate.POJO;

import java.sql.Date;
import hibernate.POJO.Person;

public class PersonInfo {
    private String id;
    private String name;
    private Date birthday;
    private String gender;
    private String role;
    private String username;
    private String password;

    public PersonInfo() {
    }

    public PersonInfo(String id, String name, Date birthday, String gender, String role, String username, String password) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.role = role;
        this.username = username;
        this.password = password;
    }


    public PersonInfo(Person a) {
        this.id = a.getId();
        this.name = a.getName();
        this.birthday = a.getBirthday();
        this.gender = (a.getGender() == 1) ? "Nam" : "Nữ";
        this.role = a.getRole();
        this.username = a.getUsername();
        this.password = a.getPassword();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
