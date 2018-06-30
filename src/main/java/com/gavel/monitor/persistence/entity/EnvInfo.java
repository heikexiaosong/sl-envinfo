package com.gavel.monitor.persistence.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SL_ENVINFO")
public class EnvInfo {

    @Id
    private String id;
    private String name;
    private String code;
    private String type;
    private Date thedate;
    private String data;

    public EnvInfo(String id, String name, String code, String type, Date thedate, String data) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.thedate = thedate;
        this.data = data;
    }

    public EnvInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getThedate() {
        return thedate;
    }

    public void setThedate(Date thedate) {
        this.thedate = thedate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("code", code)
                .append("thedate", thedate)
                .append("data", data)
                .toString();
    }
}
