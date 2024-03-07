package com.tongue.service.admin;

import com.tongue.vo.ChartByFlavorAndCategoryVO;
import com.tongue.vo.ChartVO;

import java.time.LocalDate;

public interface ChartService {
    ChartVO getDishStatistic(LocalDate begin, LocalDate end);

    ChartVO getUserStatistic(LocalDate begin, LocalDate end);
    ChartByFlavorAndCategoryVO getFlavorStatistic();
    ChartByFlavorAndCategoryVO getCategoryStatistic();
}
