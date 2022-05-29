package com.example.manageserver.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubMeal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    @NotBlank(message = "Please include a sub meal name")
    private String name;

    @NotBlank(message = "Please include a sub shortCode")
    private String shortCode;

//    @ManyToMany
//    private List<MainMeal> mainMealList;
    @ManyToOne
    private MainMeal mainMeal;

    @ManyToMany
    private List<BaseIngredient> baseIngredientList;

    private String recipeLink;

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
