import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть повне ім'я класу:");
        String className = scanner.nextLine();

        String result = Reflection_tasks.analyzeClass(className);

        System.out.println("\n=== Опис класу ===");
        System.out.println(result);
    }
}