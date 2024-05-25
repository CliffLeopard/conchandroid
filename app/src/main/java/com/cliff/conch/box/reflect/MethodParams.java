package com.cliff.conch.box.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取Method的时候getDeclaredMethod(name,class[]),第二个参数是函数的参数列表，
 * 所以映射方法的时候需要添加此注解，来标明参数；
 * 对于参数应该是隐藏类，我们为了通过编译使用了Mirror类的时候，需要加一判断通过Mirror类的TYPE字段加以替换，这些都在RefClass中完成；

 * 当然也可以使用注解MethodReflectParams，两者的目的是一致的，但MethodReflectParams使用字符串标记，反射出类型。

 * 对于不存在同名方法的情况，可以不加注解，不加注解的时候通过getDeclaredMethods获取所有方法，通过方法名称进行过滤就行。
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodParams {
    Class<?>[] value();
}