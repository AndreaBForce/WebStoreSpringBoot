package ch.supsi.webapp.web.model;

import javax.persistence.*;

@Entity
public class User {
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role ruolo;

    @Id
    @GeneratedValue
    private int id;


    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public User(String username, Role ruolo) {
        this.username = username;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRuolo() {
        return ruolo;
    }

    public void setRuolo(Role ruolo) {
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", ruolo=" + ruolo +
                ", id=" + id +
                '}';
    }
}
