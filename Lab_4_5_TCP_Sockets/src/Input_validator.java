import java.util.Scanner;

public class Input_validator
{
    // int
    public static int read_int(Scanner scanner, String prompt, int min, int max)
    {
        while (true)
        {
            System.out.print(prompt);
            if (scanner.hasNextInt())
            {
                int value = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                if (value >= min && value <= max)
                {
                    return value;
                }
                System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
            }
            else
            {
                System.out.println("Error: Not a whole number. Try again.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    // даблю
    public static double read_double(Scanner scanner, String prompt, double min, double max)
    {
        while (true)
        {
            System.out.print(prompt);
            if (scanner.hasNextDouble())
            {
                double value = scanner.nextDouble();
                scanner.nextLine(); // clear buffer
                if (value >= min && value <= max)
                {
                    return value;
                }
                System.out.println("Error: Value must be between " + min + " and " + max + ".");
            }
            else
            {
                System.out.println("Error: Invalid number format. Use dots for decimals (e.g., 50.5).");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    // string
    public static String read_string(Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
            {
                return input;
            }
            System.out.println("Error: Field cannot be empty. Please enter something.");
        }
    }
}
