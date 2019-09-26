package com.shang.demo.javabasic.innerClass;

/**
 * java内部类: 定义在类内部的类
 * 根据定义的方式不同,内部类可以分为:静态内部类,成员内部类,局部内部类,匿名内部类等
 */
public class OutClass {
    private static int a =4;
    private int b;

    /**
     * 静态内部类
     * 静态内部类和一般的类一样,可以定义静态变量,方法,构造方法等
     * OutClass.StaticInner staticInner =  new OutClass.StaticInner();
     */
    public static class StaticInner {
        private int a =3;
        public void print(){
            //静态内部类可以直接访问外部类的所有静态的变量和方法,即使private的也可以
            System.out.println(OutClass.a);
            //System.out.println(b); 但是不能访问外部类的非静态属性
            System.out.println(a);//就近原则
        }
    }

    /**
     * 成员内部类 :非静态内部类
     * 不能定义静态的方法和变量(final修饰的除外)
     *
     */
    public class Inner{

        public void print(){
            System.out.println(a); //可以访问外部类的静态变量
            System.out.println(b);
        }
    }

    /**
     * 局部内部类: 定义在方法中的类
     */
    public void test(final int c){
        final int d =1;
        class Inner{
            public void print(){
                System.out.println(c+d);
            }
        }
    }

    /**
     * 匿名内部类(要继承一个父类或者实现一个接口,直接使用new来生成一个对象的引用)
     * 注意只能继承一个父类或实现一个接口,同时没有Class关键字
     */
    public void tes2(Bird bird){
        System.out.println(bird.getName() + "能够飞 " + bird.fly() + "米");
    }

    public static void main(String[] args){
        OutClass outClass = new OutClass();
        outClass.tes2(new Bird() {
            @Override
            public int fly() {
                return 1000;
            }
            public String getName(){
                return "大雁";
            }
        });
    }

}
