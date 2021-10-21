package ch.supsi.webapp.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    private String category;

    @Id
    @GeneratedValue
    private int id;

    public Category() {
    }

    public Category(String categoria) {
         this.category = categoria;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                ", id=" + id +
                '}';
    }
}
