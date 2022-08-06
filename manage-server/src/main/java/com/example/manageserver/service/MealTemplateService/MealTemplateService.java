package com.example.manageserver.service.MealTemplateService;


import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.MealTemplate;

import java.util.List;

public interface MealTemplateService {

   MealTemplate addTemplate(MealTemplate mealTemplate);


   List<MealTemplate> getTemplates();


   List<MealTemplate> getTemplateByMealType(DayMeal.MealTypes mealTypes);

   void updateTemplate();

   void deleteTemplate(Long id);


}
