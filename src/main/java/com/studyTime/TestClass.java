package com.studyTime;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestClass {

	@Test
	public void testMethod(){
		LocalDate localDate1 = LocalDate.of(2020, 12, 02);
		LocalDate localDate2 = LocalDate.of(2020, 12, 10);
		LocalDateTime date1 = LocalDateTime.of(localDate1, LocalTime.MIN);
		LocalDateTime date2 = LocalDateTime.of(localDate2, LocalTime.MAX);
		System.out.println(date1);
		System.out.println(date2);
	}
}
