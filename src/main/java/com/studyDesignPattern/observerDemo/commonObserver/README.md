# **观察者模式**

+ # 观察者模式的特点
   1. 主题subject可以注册多个观察者，观察者的执行顺序跟注册顺序保持一致；
   2. 


+ # 观察者模式的优点
   1. 在观察者和被观察者之间，建立了一个抽象的耦合，可以很容易扩展观察者和被观察者;
   2. 观察者模式类似于广播模式，只要条件触发，观察者都会收到通知
   

+ # 观察者模式的缺点
   1. 如果观察者设置过多，每次触发都要花很长时间去处理通知;
   2. 主题与观察者之间如果存在循环依赖，可能导致系统崩溃。(还不知道怎么产生循环依赖)
  