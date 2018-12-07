package edu.gatech.curator.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "obesity_metrics")
public class ObesityMetricEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "year")
    private int year;

    @Column(name = "age")
    private String age;

    @Column(name = "underweight")
    private int underweight;

    @Column(name = "healthy")
    private int healthy;

    @Column(name = "overweight")
    private int overweight;

    @Column(name = "obese")
    private int obese;

    @Column(name = "last_updated")
    @Temporal(TemporalType.DATE)
    private Date last_updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getUnderweight() {
        return underweight;
    }

    public void setUnderweight(int underweight) {
        this.underweight = underweight;
    }

    public int getHealthy() {
        return healthy;
    }

    public void setHealthy(int healthy) {
        this.healthy = healthy;
    }

    public int getOverweight() {
        return overweight;
    }

    public void setOverweight(int overweight) {
        this.overweight = overweight;
    }

    public int getObese() {
        return obese;
    }

    public void setObese(int obese) {
        this.obese = obese;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }
}
