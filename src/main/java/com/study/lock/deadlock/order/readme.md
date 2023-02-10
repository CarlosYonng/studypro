###LeftRightDeadLock 
该类中方法数据简单的锁顺序死锁，
若两个线程获取锁的时机不当可能产生：  
A ->  锁住left  ->   尝试锁住right  ->  永久等待
    B ->  锁住right  ->   尝试锁住left  ->  永久等待


###DynamicLock
* 该类中方法作为一个简单的两个账户相互转账方法，内部看起来没问题，按顺序获取A账户
再获取B账户，
但若两个线程同时请求，并且两个线程调用方法传参顺序相反，那就可能产
生第一个线程先锁A账户，第二个线程先锁B账户也会出现死锁，
例子为：
A：transferMoney(A, B, 10);
A：transferMoney(B, A, 20);
这种死锁就是动态的锁顺序的死锁

+ 一般处理方案为： transferMoney2()方法
  + 该方案为将两个参数设置为final类型并通过System.identityHashCode方法，
  该方法将返回Object.hashcode的返回值，计算hash值后不论参数如何传递，永
  远先获取hash值较小的对象的锁，保证多线程下获取锁的顺序不会影响程序；
  + 但还有一种情况为，当两个对象的hash值结果相同（hash冲突）时，可以设置一个
  “加时赛”锁，该锁使用的话，就可以保证每次只有一个线程以未知的顺序来获取这两
  个锁（即：相当于将多线程并发处理任务变为了单线程加锁处理）。


