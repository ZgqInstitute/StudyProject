RandomAccessFile

说明：
1-该对象即能读也能写，通过RandomAccessFile构造参数model来进行控制
  model是指定用以打开文件的访问模式，其值有：
     "r"：以只读方式打开，若调用写write方法则抛异常IOException 
     "rw"：可读可写
     "rws"
     "rwd"
2-RandomAccessFile内部维护了一个byte数组，并且可以通过指针来操作数组中的任意位置
3-getFilePoint方法是获取当前指针的位置，seek方法是设置指针的位置。随机访问就是通过这两个方法来实现的
4-RandomAccessFile对象之所以可以读也可以写，是该对象封装了字节输出流和字节输入流
5-RandomAccessFile对象的源 和 目的只能是文件


作用：使用RandomAccessFile可以实现多线程写入【断点续传】
     因为RandomAccessFile可以通过getFilePoint方法和seek方法指定读写任意数组位置，（其他流只能从头开始读写，不能指定位置读写）
     那么可以同时开启多个线程，分段同时操作文件，比如：
     第一个线程封装一个RandomAccessFile对象，读写第0-1000个字节；
     第二个线程封装一个RandomAccessFile对象，读写第1001-2000个字节；
     第三个线程封装一个RandomAccessFile对象，读写第2001-3000个字节；



