
使用管道流PipedInputStream和PipedOutputStream要结合多线程使用，
因为若不使用多线程，若先执行了read()方法，有可能出现阻塞

使PipedInputStream和PipedOutputStream产生关系有二种方法：
1-在new PipedInputStream(PipedOutputStream)时，通过构造传入PipedOutputStream
2-先创建PipedInputStream对象，再调用PipedInputStream对象的connect()方法





