package com.shang.demo.javabasic.serializable;

import java.io.Serializable;

/**
 * 需要注意的点
 * 对象序列化:保存(持久化)对象及其状态到内存或者磁盘
 * 保存对象时,会把其状态保存为一组字节,在未来在将这些字节组装成对象
 * 注意:对象序列化不会关注类中的静态变量
 *     除了在持久化对象时会用到对象序列化之外，当使用 RMI(远程方法调用)，或在网络中传递对象时，都会用到对象序列化
 * 方法:
 *  只要一个类实现了 java.io.Serializable 接口，那么它就可以被序列化
 *  通过 ObjectOutputStream 和 ObjectInputStream 对对象进行序列化及反序列化
 *  在类中增加 writeObject 和 readObject 方法可以实现自定义序列化策略
 * 序列化 ID
 *  虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个
 *  类的序列化 ID 是否一致(就是 private static final long serialVersionUID)
 * Transient 关键字,可以阻止某个参数被序列化到文件中
 */
public class Tips implements Serializable {

    private static final long serialVersionUID= 123L ;


}
