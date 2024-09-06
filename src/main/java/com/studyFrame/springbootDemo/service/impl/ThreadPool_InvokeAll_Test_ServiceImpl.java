package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.CarProductDTO;
import com.studyFrame.springbootDemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ThreadPool_InvokeAll_Test_ServiceImpl {

    @Autowired
    private ExecutorService commonThreadPool;

    public List<CarProductDTO> useInvokeAll() {
        // 创建任务列表
        List<TaskService> tasks = new ArrayList<>();
        TaskServiceDiDiImpl taskServiceDiDi = new TaskServiceDiDiImpl();
        TaskServiceCaoCaoImpl taskServiceCaoCao = new TaskServiceCaoCaoImpl();
        TaskServiceT3Impl taskServiceT3 = new TaskServiceT3Impl();
        tasks.add(taskServiceDiDi);
        tasks.add(taskServiceCaoCao);
        tasks.add(taskServiceT3);
        try {
            List<Future<List<CarProductDTO>>> futures = commonThreadPool.invokeAll(tasks, 1, TimeUnit.SECONDS);
            List<CarProductDTO> resultList = new ArrayList<>();
            /**
             * 得到任务迭代器。迭代器的顺序与futures的顺序是一致的。
             * 任务是按照滴滴、曹操、T3的顺序放入tasks中的，那么执行完invokeAll后得到的结果futures也是对应滴滴、曹操、T3的结果
             */
            Iterator<TaskService> iterator = tasks.iterator();
            for (Future<List<CarProductDTO>> future : futures) {
                TaskService next = iterator.next();
                try {
                    boolean cancelled = future.isCancelled();
                    System.out.println(next.taskName() + "是否超时取消 = " + cancelled);
                    if(cancelled){
                        // 若invokeAll的超时时间到了任务还没有执行结束，那么调用future.get()会抛CancellationException异常
                        // 若任务超时直接进入下一次循环，不调future.get()
                        System.out.println(next.timeOutMessage());
                        continue;
                    }
                    List<CarProductDTO> list = future.get();
                    System.out.println(next.succMessage());
                    System.out.println("临时打印：" + list);
                    resultList.addAll(list);
                } catch (CancellationException e) {
                     //
                    System.out.println(next.timeOutMessage());
                    // 因为超时的任务结果已经不使用，对于超时的任务要将其终止，避免浪费线程资源

                } catch (Exception e) {
                    System.out.println(next.errorMessage() + "，异常消息 = " + e.getMessage());
                }
            }

            System.out.println("聚合结果 = " + resultList);
            return resultList;
        } catch (Exception e) {
            System.out.println("异常 = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
