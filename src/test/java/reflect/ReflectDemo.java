package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 崔崔
 * @date 2020/8/3-18:22
 * 小崔hello
 */
public class ReflectDemo {
    public static void main(String[] args) throws Exception {
        //反射：java代码在运行时动态获取一个类的属性和方法，或者调用一个对象的属性和方法。
        Student s = new Student();
        //实现反射，必须要有字节码对象
        //Class 字节码对象 约等于.class 文件，拿到字节码对象相当于拿到整个类所有信息
        Class clazz1 =Student.class;
        System.out.println(clazz1);
        Class clazz2 =s.getClass();
        String className = "reflect.Student";
       Class clazz3 = Class.forName(className);
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);

        Object o = clazz3.newInstance();
        System.out.println(o);
        //调用属性--》先获取属性，再通过对象调用
        Field field = clazz3.getField("name");
        //field.getName();
        field.set(o,"zhangsan");
        System.out.println(o);
        System.out.println(field.get(o));
        //调用方法--》先获取属性，再通过对象调用
        Method method = clazz3.getMethod("eat");
       // clazz3.getMethod("study");
        Object result = method.invoke(o);
        System.out.println(result);
    }
}
