package com.studyJDKAPI.studyToolClass.ArraysCase;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

public class SortDemo {

    @Test
    public void sortTest() {
        Student s1 = new Student(18);
        Student s2 = new Student(20);
        Student s3 = new Student(10);
        Student[] stuArray = {s1, s2, s3};
        printStudentAge(stuArray);

        // 按年龄进行排序
        // 写法一
        Arrays.sort(stuArray, new StudentComparator());
        // 写法二
//        Arrays.sort(stuArray, new Comparator<Student>(){
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o1.age - o2.age;
//            }
//        });
        // 写法三
//        Arrays.sort(stuArray, (o1, o2) -> o1.age - o2.age);
        // 写法四
//        Arrays.sort(stuArray, Comparator.comparingInt(o -> o.age));

        printStudentAge(stuArray);
    }

    public void printStudentAge(Student[] stu){
        for(Student d: stu){
            System.out.print(d.age + " " );
        }
        System.out.println();
    }

    class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

    class Student {
        int age;

        public Student(int s) {
            age = s;
        }
    }
}
