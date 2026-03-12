import java.util.Scanner;
import java.lang.reflect.Array;

public class Main
{
    public static void main(String[] args)
    {
        // main task
        System.out.println("Перевірка завдання 1:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть повне ім'я класу: ");
        String className = scanner.nextLine();
        String result = Reflection_tasks.analyze_class(className);
        System.out.println("\nОпис класу: ");
        System.out.println(result);

        // first task
        System.out.println("\nПеревірка завдання 2:");
        Cat myCat = new Cat("Пуся", 4, "Біленька");
        Reflection_tasks.analyze_object_state(myCat);
        scanner.close();

        // second task
        System.out.println("\nПеревірка завдання 3:");
        try
        {
            Object res = Reflection_tasks.invoke_method_by_name(myCat, "get_age_human_years");
            System.out.println("Результат виклику: " + res);

            // System.out.println("Пробуємо викликати неіснуючий метод 'fly'...");
            // Reflection_tasks.invoke_method_by_name(myCat, "fly");

        }
        catch (FunctionNotFoundException e)
        {
            System.err.println(e.getMessage());
        }

        // third task
        System.out.println("\nПеревірка завдання 4:");
        // one-dimensional array
        Object int_array = Reflection_tasks.create_array(int.class, 3);
        Array.set(int_array, 0, 10);
        Array.set(int_array, 1, 20);
        Array.set(int_array, 2, 30);
        System.out.println("Створено масив int: " + Reflection_tasks.array_to_string(int_array));
        int_array = Reflection_tasks.resize_array(int_array, 5);
        System.out.println("Після збільшення розміру: " + Reflection_tasks.array_to_string(int_array));
        System.out.println("---");

        // two-dimensional array
        Object string_matrix = Reflection_tasks.create_matrix(String.class, 2, 2);
        Object row0 = Array.get(string_matrix, 0);
        Array.set(row0, 0, "A");
        Array.set(row0, 1, "B");
        Object row1 = Array.get(string_matrix, 1);
        Array.set(row1, 0, "C");
        Array.set(row1, 1, "D");
        System.out.println("Створено матрицю String: " + Reflection_tasks.array_to_string(string_matrix));
        string_matrix = Reflection_tasks.resize_matrix(string_matrix, 3, 3);
        System.out.println("Після розширення матриці: " + Reflection_tasks.array_to_string(string_matrix));

        // fourth task

    }
}

class Cat
{
    // private fields
    private String name;
    private int age;
    public String color;

    public Cat(String name, int age, String color)
    {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    // public method without parameters
    public void meow()
    {
        System.out.println(name + " каже: мяу мяу!");
    }

    // public method without parameters, that returns something
    public int get_age_human_years()
    {
        return age * 7;
    }

    // private method
    private void sleep()
    {
        System.out.println("Кіт спить...");
    }

    // public method with parameters
    public void eat(String food)
    {
        System.out.println("Кіт їсть " + food);
    }
}