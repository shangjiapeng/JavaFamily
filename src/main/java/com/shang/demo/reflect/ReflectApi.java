package com.shang.demo.reflect;


import com.shang.demo.pojo.Student;
import com.shang.demo.pojo.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此类用于测试 反射API
 * Class 类: 反射的核心类,可以获取类的属性方法.
 * Field 类: java.lang.reflec 包中的类,表示类的成员变量,可以用来获取和设置类之中的属性值.
 * Method 类: java.lang.reflec包中的类,表示类的方法,可以用来获取类中的方法信息或者执行方法.
 * Construct类: java.lang.reflec包中的类,表示类的构造方法.
 */
public class ReflectApi {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        User user =new Student();
        //调用 对象 的getClass()方法
        Class clazz = user.getClass();
        //调用 类 的class属性来获得该类对应的Class对象
        Class clazz1 = User.class;
        //调用Class 类的forName()静态方法(最安全,性能最好)
        Class clazz2 = Class.forName("com.shang.demo.pojo.User");
        //获取类的所有方法信息
        Method[] methods = clazz2.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.toString());
        }
        //获取类的所有成员属性信息
        Field[] declaredFields = clazz2.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.toString());
        }
        //获取类的所有构造方法信息
        Constructor[] declaredConstructors = clazz2.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors) {
            System.out.println(constructor.toString());
        }
        //创建对象的两种方法
        //1 使用Class对象的newInstance()方法,来创建该class对象对应的实例,要求Class对象对应的类有默认的空的构造器,
        Class clazz3 = Class.forName("com.shang.demo.pojo.User");//类的全路径
        User user1 = (User) clazz3.newInstance();

        //2 先使用 Class 对象获取指定的 Constructor 对象，再调用 Constructor 对象的 newInstance()
        //方法来创建 Class 对象对应类的实例,通过这种方法可以选定构造方法创建实例。
        Constructor construct = clazz3.getDeclaredConstructor(long.class, String.class, int.class, String.class);
        //创建对象并设置属性
        User tom = (User) construct.newInstance(1L, "Tom", 18, "1314@qq.com");


    }






}
