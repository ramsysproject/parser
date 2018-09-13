package com.ef.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Restriction {

    private Long id;
    private String ipAddress;
    private String motive;

    public Restriction(String ipAddress, String motive) {
        this.ipAddress = ipAddress;
        this.motive = motive;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public static class Builder {
        private String ipAddress;
        private String motive;

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder motive(String motive) {
            this.motive = motive;
            return this;
        }

        public Restriction build() {
            return new Restriction(ipAddress, motive);
        }
    }
}
