package reflect;

/**
 * @author 崔崔
 * @date 2020/8/3-18:21
 * 小崔hello
 */
public class Student {
    public String name;
    private int age;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void eat(){
        System.out.println("Student.eat");
    }
    private void study(){
        System.out.println("Student.study");
    }
}
