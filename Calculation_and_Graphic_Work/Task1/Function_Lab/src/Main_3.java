import functions.StringFunction;
import utils.FileWork_2;
import utils.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.TreeSet;

// Task 3
public class Main_3
{
    public static void main(String[] args)
    {
        System.out.println("\tЛаба 1 - (Завдання 3)");

        // CSV reading and advanced collections
        System.out.println("Етап 1: Читання CSV та колекції\n");
        String filename = "test.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(filename)))
        {
            pw.println("1.5, 0.997");
            pw.println("2.0, 0.909");
            pw.println("2.5, 0.598");
            pw.println("3.0, 0.141");
            System.out.println("тестік файлік '" + filename + "' згенеровано!\n");
        }
        catch (IOException e)
        {
            System.out.println("ошибочка створення файліку: " + e.getMessage());
            return;
        }

        FileWork_2 fileWork = new FileWork_2();

        TreeMap<Double, Double> myTreeMap = new TreeMap<>();
        TreeSet<Point> myTreeSet = new TreeSet<>();

        fileWork.read_csv_to_collections(filename, myTreeMap, myTreeSet);

        if (!myTreeSet.isEmpty())
        {
            System.out.println("\nперевірочка TreeSet (найменша точка по Х): " + myTreeSet.first());
            System.out.println("перевірочка TreeMap (найменша точка по Х): " + myTreeMap.firstEntry());
        }
        else
        {
            System.out.println("чота порожні колекції... чекни, чи є дані у файліку " + filename + "...");
        }

        // dynamic string function and analytical derivative
        System.out.println("Етап 2: Аналітична похідна з тексту");

        String str1 = "exp(-x)*sin(x)";
        StringFunction f1 = new StringFunction(str1);
        System.out.println("\nтестік 1 (звичайна функція без параметра):");
        System.out.println("функція: f(x) = " + str1);
        System.out.println("аналітична похідна: f'(x) = " + f1.getDerivativeFormula());
        System.out.println("результат f(1.5) = " + f1.calculate(1.5));
        System.out.println("результат f'(1.5) = " + f1.calculateAnalyticalDerivative(1.5));

        double a = 1.5;
        String str2 = "exp(-a*x)*sin(x)";
        StringFunction f2 = new StringFunction(str2, a);
        System.out.println("\nтестік 2 (з параметром a = " + a + "):");
        System.out.println("функція: f(x) = " + str2);
        System.out.println("аналітична похідна: f'(x) = " + f2.getDerivativeFormula());
        System.out.println("результат f(1.5) = " + f2.calculate(1.5));
        System.out.println("результат f'(1.5) = " + f2.calculateAnalyticalDerivative(1.5));

        // launch graphic user interface
        System.out.println("\nЕтап 3: Запуск графічного інтерфейсу");
        new MainWindow().setVisible(true);
    }
}
