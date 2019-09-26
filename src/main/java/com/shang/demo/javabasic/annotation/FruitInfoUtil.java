package com.shang.demo.javabasic.annotation;

import java.lang.reflect.Field;

/**
 * 注解处理类
 */
public class FruitInfoUtil {
    public static void main(String[] args) {
        getFruitInfo(Apple.class);
    }

    public static void getFruitInfo(Class<?> clazz){
        String fruitProviderString = "供应商信息: ";
        Field[] fields = clazz.getDeclaredFields(); //通过反射获取处理注解
        for (Field field : fields) {
            //如果这个字段被该上面有这个注解
            if (field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                //注解信息的处理方法
                fruitProviderString="供应商编号:"+fruitProvider.id()+"\n供应商名称:"+ fruitProvider.name()+"\n供应商地址:"+fruitProvider.address();
                System.out.println(fruitProviderString);
            }
        }

    }
}
