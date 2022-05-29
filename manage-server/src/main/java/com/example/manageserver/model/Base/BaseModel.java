package com.example.manageserver.model.Base;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Inheritance
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    public Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    public Date updated_At;


    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }
}
