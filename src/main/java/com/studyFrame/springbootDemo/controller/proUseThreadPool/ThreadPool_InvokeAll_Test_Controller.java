package com.studyFrame.springbootDemo.controller.proUseThreadPool;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;
import com.studyFrame.springbootDemo.service.impl.ThreadPool_InvokeAll_Test_ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/threadtest")
public class ThreadPool_InvokeAll_Test_Controller {

    @Autowired
    private ThreadPool_InvokeAll_Test_ServiceImpl threadPoolInvokeAllTestService;

    @GetMapping("/get")
    public List<CarProductDTO> get() {
        return threadPoolInvokeAllTestService.useInvokeAll();
    }


}
