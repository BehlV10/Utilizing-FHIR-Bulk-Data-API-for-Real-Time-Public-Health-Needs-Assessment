package edu.gatech.curator.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "patient")
@IdClass(PatientEntity.PatientPrimaryKey.class)
public class PatientEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Id
    @Column(name = "source_system_id")
    private long sourceSystemId;

    @Column(name = "source_system_name")
    private String sourceSystemName;

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(long sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getSourceSystemName() {
        return sourceSystemName;
    }

    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    public static class PatientPrimaryKey implements Serializable {
        private String id;
        private long sourceSystemId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getSourceSystemId() {
            return sourceSystemId;
        }

        public void setSourceSystemId(long sourceSystemId) {
            this.sourceSystemId = sourceSystemId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PatientPrimaryKey that = (PatientPrimaryKey) o;
            return sourceSystemId == that.sourceSystemId &&
                    Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, sourceSystemId);
        }
    }
}
