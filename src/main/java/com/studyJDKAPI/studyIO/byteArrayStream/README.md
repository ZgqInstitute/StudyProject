字节数组流ByteArrayStream的源和目的都是内存
   ByteArrayInputStream的源是内存
   ByteArrayOutputStream的目的也是内存

因为ByteArrayStream的源和目的都是内存，因此不需要调用close()方法，因为没有调用操作系统的资源，无须关闭