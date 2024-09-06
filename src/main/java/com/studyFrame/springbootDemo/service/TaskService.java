package com.studyFrame.springbootDemo.service;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 任务接口
 * 每个具体的任务需要实现改接口
 */
public interface TaskService extends Callable<List<CarProductDTO>> {

    String taskName();

    String timeOutMessage();

    String succMessage();

    String errorMessage();

}
