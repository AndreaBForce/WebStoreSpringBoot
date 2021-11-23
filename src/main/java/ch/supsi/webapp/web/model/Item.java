package ch.supsi.webapp.web.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Date;

import javax.persistence.*;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Item {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    private Date date;

    private String annuncio;

    @Lob
    private byte[] image;

    @Id
    @GeneratedValue
    private int id;

    public Item() {
    }

    public Item(String title, String description, User author, Category category) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
    }

    public Item(String title, String description, User author, Category category, Date date, String annuncio, int id) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.date = date;
        this.annuncio = annuncio;
        this.id = id;
    }

    public Item(String title, String description, User author, Category category, Date date, String annuncio, int id,byte[] image) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.date = date;
        this.annuncio = annuncio;
        this.id = id;
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAnnuncio() {
        return annuncio;
    }

    public void setAnnuncio(String annuncio) {
        this.annuncio = annuncio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User authore) {
        this.author = authore;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", id=" + id +
                '}';
    }
}
