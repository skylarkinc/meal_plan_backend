package com.example.manageserver.service.MealTemplateService;

import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.MealTemplate;
import com.example.manageserver.repository.MealTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealTemplateServiceImpl implements MealTemplateService {


    @Autowired
    MealTemplateRepository mealTemplateRepository;

    public MealTemplate addTemplate(MealTemplate mealTemplate) {
        return this.mealTemplateRepository.save(mealTemplate);
    }

    @Override
    public List<MealTemplate> getTemplates() {
        return this.mealTemplateRepository.findAll();
    }

    @Override
    public List<MealTemplate> getTemplateByMealType(DayMeal.MealTypes mealTypes) {
        return this.mealTemplateRepository.findByMealType(mealTypes);
    }

    @Override
    public void updateTemplate() {

    }

    @Override
    public void deleteTemplate(Long id) {
        this.mealTemplateRepository.deleteById(id);
    }
}
