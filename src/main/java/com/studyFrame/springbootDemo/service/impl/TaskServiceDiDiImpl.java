package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;
import com.studyFrame.springbootDemo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskServiceDiDiImpl implements TaskService {

    @Override
    public String taskName() {
        return "滴滴";
    }

    @Override
    public String timeOutMessage() {
        return "滴滴任务超时";
    }

    @Override
    public String succMessage() {
        return "滴滴正常返回";
    }

    @Override
    public String errorMessage() {
        return "滴滴任务异常";
    }

    @Override
    public List<CarProductDTO> call() throws Exception {
        CarProductDTO carProductDTO1 = new CarProductDTO();
        carProductDTO1.setSupplierName("滴滴");
        carProductDTO1.setBrand("比亚迪");
        carProductDTO1.setCarModel("唐");
        carProductDTO1.setMoney(80);

        return Arrays.asList(carProductDTO1);
    }
}
