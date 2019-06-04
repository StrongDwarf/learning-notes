public class Employee{
    private String name;
    private int age;

    public Employee(){
        this.name = "xiaobaicai";
        this.age = 21;
    }

    public Employee(String name,int age){
        this.name = name;
        this.age = age;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }

    public static void println(String str){
        System.out.println(str);
    }

    public static void swap(Employee x,Employee y){
        println("swap start");
        println("x.name" + x.getName());
        println("y.name" + y.getName());
        Employee temp = x;
        x = y;
        y = temp;
        println("swap end");
        println("x.name" + x.getName());
        println("y.name" + y.getName());
    }

    public static void main(String[] args){
        Employee a = new Employee("a",21);
        Employee b = new Employee("b",21);
        swap(a,b);
        println("a.name" + a.getName());
        
    }
}