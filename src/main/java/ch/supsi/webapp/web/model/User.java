package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    private String username;

    private String nome;

    private String cognome;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role ruolo;

    @Id
    @GeneratedValue
    private int id;

    private String password;

    @OneToMany(mappedBy = "utente")
    List<Item> confrontabili = new ArrayList<>();

    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public User(String username, Role ruolo) {
        this.username = username;
        this.ruolo = ruolo;
    }

    public User(String username, Role ruolo, String password) {
        this.username = username;
        this.ruolo = ruolo;
        this.password = password;
    }

    public User(String username, String nome, String cognome, Role ruolo, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.password = password;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getConfrontabili() {
        return confrontabili;
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
