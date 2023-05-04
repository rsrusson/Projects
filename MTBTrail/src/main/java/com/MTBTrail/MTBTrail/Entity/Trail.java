package com.MTBTrail.MTBTrail.Entity;

import jakarta.persistence.*;

@Entity
@Table(name= "trail")
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "length")
    private Float length;

    @Column(name = "difficulty")
    private String difficulty;

    public Trail() {
    }

    public Trail(int id, String name, String location, Float length, String difficulty) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.length = length;
        this.difficulty = difficulty;
    }

    public Trail(String name, String location, Float length, String difficulty) {
        this.name = name;
        this.location = location;
        this.length = length;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Trail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", length=" + length +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
