package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;
import com.studyFrame.springbootDemo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskServiceCaoCaoImpl implements TaskService {

    @Override
    public String taskName() {
        return "曹操";
    }

    @Override
    public String timeOutMessage() {
        return "曹操任务超时";
    }

    @Override
    public String succMessage() {
        return "曹操正常返回";
    }


    @Override
    public String errorMessage() {
        return "曹操任务异常";
    }

    @Override
    public List<CarProductDTO> call() throws Exception {
        long x = 100000000000L;
        while (x > 0) {
            x--;
        }
        CarProductDTO carProductDTO1 = new CarProductDTO();
        carProductDTO1.setSupplierName("曹操");
        carProductDTO1.setBrand("奔驰");
        carProductDTO1.setCarModel("GLB");
        carProductDTO1.setMoney(100);

        CarProductDTO carProductDTO2 = new CarProductDTO();
        carProductDTO2.setSupplierName("曹操");
        carProductDTO2.setBrand("宝马");
        carProductDTO2.setCarModel("X1");
        carProductDTO2.setMoney(98);
        System.out.println("曹操执行结束。。。。");
        return Arrays.asList(carProductDTO1, carProductDTO2);
    }
}
