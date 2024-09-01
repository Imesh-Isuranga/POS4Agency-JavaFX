package lk.crossorigin.agency.entity;

import java.util.Date;

public class SaveList {

    private int id;
    private String description;

    public SaveList() {
    }

    public SaveList(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaveList{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
