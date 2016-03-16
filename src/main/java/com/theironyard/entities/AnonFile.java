package com.theironyard.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by zach on 3/16/16.
 */
@Entity
@Table(name = "files")
public class AnonFile {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String filename;

    @NotNull
    private String originalFilename;

    @NotNull
    private String comment;

    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    @NotNull
    private boolean isPerm = false;

    public AnonFile() {
    }

    public AnonFile(String filename, String originalFilename, String comment) {
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public boolean isPerm() {
        return isPerm;
    }

    public void setPerm(boolean perm) {
        isPerm = perm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
