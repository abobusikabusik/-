import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Перевірка завдання 1: \n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть повне ім'я класу: ");
        String className = scanner.nextLine();

        String result = Reflection_tasks.analyze_class(className);

        System.out.println("\nОпис класу: ");
        System.out.println(result);

        System.out.println("Перевірка завдання 2: \n");
        Cat myCat = new Cat("Пуся", 4, "Біленька");

        Reflection_tasks.analyze_object_state(myCat);
        scanner.close();
    }
}

class Cat
{
    // Приватні поля
    private String name;
    private int age;
    public String color;

    public Cat(String name, int age, String color)
    {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    // Публічний метод без параметрів
    public void meow()
    {
        System.out.println(name + " каже: мяу мяу!");
    }

    // Публічний метод без параметрів, який щось повертає
    public int get_age_human_years()
    {
        return age * 7;
    }

    // Приватний метод
    private void sleep()
    {
        System.out.println("Кіт спить...");
    }

    // Публічний метод з параметром
    public void eat(String food)
    {
        System.out.println("Кіт їсть " + food);
    }
}