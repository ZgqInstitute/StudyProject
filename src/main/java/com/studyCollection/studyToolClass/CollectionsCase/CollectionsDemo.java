package com.studyCollection.studyToolClass.CollectionsCase;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class CollectionsDemo {

    /**
     * EmptyList是AbstractList的子类；AbstractList是List的子类
     *
     * emptyList方法的作用：返回一个空的List（使用前提是不会再对返回的list进行增加和删除操作）；
     * 好处：
     *     1. new ArrayList()创建时有初始大小，占用内存，emptyList()不用创建一个新的对象，可以减少内存开销；
     *     2. 方法返回一个emptyList()时，不会报空指针异常，如果直接返回Null，没有进行非空判断就会报空指针异常；
     * 注意：使用Collections.emptyList()得到的List与常用的List不同，EmptyList是Collections类里的静态内部类，
     *      在继承AbstractList后并没有实现add()、remove()等方法，所有调用EmptyList的add()、remove()方法实际
     *      调用的是父类AbstractList的add()、remove()方法，而父类AbstractList的add()、remove()方法是直接抛
     *      出UnsupportedOperationException异常，所以EmptyList不能使用add()、remove()等方法。
     */
    @Test
    public void emptyList_test(){
        String str = "";
        List<String> list = getList(str);
        System.out.println(list.size());
    }

    private List<String> getList(String str) {
        if (StringUtils.isEmpty(str)) {
            // 返回的List为空  size为0
            return Collections.emptyList();
            // 若直接返回null，后面使用getList()方法的返回值就需要先做判空操作，不然容易发送NPE
            // return null;
            // 若直接返回new ArrayList<>()，因为new ArrayList<>()会初始化，占用内存
            // return new ArrayList<>();
        }
        return new ArrayList<>();
    }

}
