
1-序列化和反序列化的作用：
   （1）序列化也可以叫持久化，若有些对象（如数据库的连接对象）想要延长对象的生命周期就可以将对象序列化，用的时候直接反序列化就行
   （2）对象可以序列化后，就可以实现存储对象到硬盘、将对象进行网络传输
  ObjectOutputStream：把对象从内存写到外围设备，也叫持久化，也叫序列化
  ObjectInputStream：从外围设备读对象到内存，也叫反序列化




2-Serializable接口
  2.1 一旦类实现了Serializable接口，那么该类（是类有）默认就有一个对应的serialVersionUID，在序列化时，会将该序列号一起序列化写到文件中，
      在反序列化时，会拿序列化后文件的序列号和类的序列号进行对比，若相等则序列化成功，若不等则抛InvalidClassException异常
      所以，Serializable接口的作用就是给类加序列号serialVersionUID，序列号serialVersionUID是用于判断类和对象版本是否一致
  2.2 Q：序列号serialVersionUID怎么算出来？
      A：是根据类的特征，如类的属性，签名等计算出来的，只要类的属性或修饰符发生改变，那么类的序列号serialVersionUID也会随之改变
  2.3 Q：为什么强烈建议在类中显示声明序列号serialVersionUID
      A：因为同一个类，对于不同的编译器在计算序列号serialVersionUID时，可能会计算出不同结果的值，这样会导致反序列化失败抛InvalidClassException异常
