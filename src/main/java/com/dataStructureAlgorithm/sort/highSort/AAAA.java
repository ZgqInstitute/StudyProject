package com.dataStructureAlgorithm.sort.highSort;

import org.junit.Test;

public class AAAA {

	@Test
	public void testMethod() {
		int result = diGui(5);
		System.out.println(result);
	}

	//n = 5
	//n = 4
	//n = 3
	//n = 2
	//n = 1
	public int diGui(int n) {
		if (n <= 1) {
			return 1;
		}
		int temp;
		//diGui()方法第 1 次入栈
		//diGui()方法第 2 次入栈
		//diGui()方法第 3 次入栈
		//diGui()方法第 4 次入栈
		System.out.println("diGui()方法第" + (5 - n + 1) + "次《入》栈");
		//temp = 5 * diGui(4)
		//temp = 5 * diGui(4) * diGui(3)
		//temp = 5 * diGui(4) * diGui(3) * diGui(2)
		//temp = 5 * diGui(4) * diGui(3) * diGui(2) * diGui(1)
		temp = n * diGui(n - 1);
		System.out.println("diGui()方法第" + (n - 1) + "次《出》栈");
		return temp;
	}
}
