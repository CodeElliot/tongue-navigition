package com.tongue.service.admin;

import com.tongue.dto.StepDTO;
import com.tongue.entity.Step;

public interface StepService {
    void addStep(Step step);

    void deleteByDishId(Long id);

    void update(Step step);
}
