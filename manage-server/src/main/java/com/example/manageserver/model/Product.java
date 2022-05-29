package com.example.manageserver.model;

import com.example.manageserver.model.Base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    private String name;

    private String shortCode;

    @OneToOne
    private Unit unit;

    @OneToOne
    private Supplier supplier;

   @OneToOne
    private MainProductCategory mainCategory;

   @OneToOne
    private SubProductCategory subProductCategory;

   private String description;


   private Double unitPrice;


    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

}
