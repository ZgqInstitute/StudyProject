baseStream主要学习以下流：
（1）字符流：
     FileWriter  BufferedWriter  OutputStreamWriter
     FileReader  BufferedReader  InputStreamReader
（2）字节流：
     FileOutputStream  BufferedOutputStream
     FileInputStream   BufferedInputStream
 
 以上流对象只能操作设备上的数据，但是不能：
   操作文件夹、
   操作文件的属性（创建时间修改时间）、
   操作文件名、
   操作文件只读、只写
 要操作这些就需要使用File对象
