package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;
import com.studyFrame.springbootDemo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskServiceT3Impl implements TaskService {

    @Override
    public String taskName() {
        return "T3";
    }

    @Override
    public String timeOutMessage() {
        return "T3任务超时";
    }

    @Override
    public String succMessage() {
        return "T3正常返回";
    }

    @Override
    public String errorMessage() {
        return "T3任务异常";
    }

    @Override
    public List<CarProductDTO> call() throws Exception {
        int x = 3/0;
        CarProductDTO carProductDTO1 = new CarProductDTO();
        carProductDTO1.setSupplierName("T3");
        carProductDTO1.setBrand("大众");
        carProductDTO1.setCarModel("途岳");
        carProductDTO1.setMoney(70);

        CarProductDTO carProductDTO2 = new CarProductDTO();
        carProductDTO2.setSupplierName("T3");
        carProductDTO2.setBrand("丰田");
        carProductDTO2.setCarModel("汉兰达");
        carProductDTO2.setMoney(66);
        return Arrays.asList(carProductDTO1, carProductDTO2);
    }
}
