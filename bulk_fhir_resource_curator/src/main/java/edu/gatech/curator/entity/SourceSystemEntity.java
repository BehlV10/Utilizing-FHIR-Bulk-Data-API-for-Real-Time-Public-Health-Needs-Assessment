package edu.gatech.curator.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "source_system")
public class SourceSystemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "kid")
    private String kid;

    @Column(name = "jku")
    private String jku;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "tokenPath")
    private String tokenPath;

        @Column(name = "fhirServerPath")
    private String fhirServerPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getJku() {
        return jku;
    }

    public void setJku(String jku) {
        this.jku = jku;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTokenPath() {
        return tokenPath;
    }

    public void setTokenPath(String tokenPath) {
        this.tokenPath = tokenPath;
    }

    public String getFhirServerPath() {
        return fhirServerPath;
    }

    public void setFhirServerPath(String fhirServerPath) {
        this.fhirServerPath = fhirServerPath;
    }
}
