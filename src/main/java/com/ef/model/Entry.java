package com.ef.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Entry {

    private Long id;
    private Date date;
    private String ipAddress;
    private String request;
    private Integer status;
    private String userAgent;

    public Entry(Date date, String ipAddress, String request, Integer status, String userAgent) {
        this.date = date;
        this.ipAddress = ipAddress;
        this.request = request;
        this.status = status;
        this.userAgent = userAgent;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", request='" + request + '\'' +
                ", status=" + status +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    public static class Builder {
        private Date date;
        private String ipAddress;
        private String request;
        private Integer status;
        private String userAgent;

        public Builder date(Date date){
            this.date = date;
            return this;
        }

        public Builder ipAddress(String ipAddress){
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder request(String request){
            this.request = request;
            return this;
        }

        public Builder status(Integer status){
            this.status = status;
            return this;
        }

        public Builder userAgent(String userAgent){
            this.userAgent = userAgent;
            return this;
        }

        public Entry build(){
            return new Entry(date, ipAddress, request, status, userAgent);
        }
    }
}
