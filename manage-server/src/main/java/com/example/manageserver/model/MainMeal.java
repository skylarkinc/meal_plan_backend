package com.example.manageserver.model;
import com.example.manageserver.model.Base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class MainMeal extends BaseModel {

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

    private String description;

    @OneToMany
    private List<SubMeal> subMealList;

    @PrePersist
    protected void onCreate() {

        this.created_At = new Date();
        this.setShortNameIfNull();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
        this.setShortNameIfNull();
    }

    private void setShortNameIfNull(){
        if(this.shortCode==null)
            this.setShortCode(this.getName().trim());
    }
}
