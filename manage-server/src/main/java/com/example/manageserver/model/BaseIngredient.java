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
public class BaseIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

//    @NotBlank(message = "Please include a base ingredient meal name")
//    private String name;
//
//    @NotBlank(message = "Please include a base ingredient shortCode")
//    private String shortCode;

//    @ManyToMany
//    private List<SubMeal> subMealList;

    @OneToOne
    private Product product;

    @OneToOne
    private Unit unit;

    private double quantity;

}
