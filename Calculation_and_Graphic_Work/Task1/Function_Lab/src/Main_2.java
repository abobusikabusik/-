import functions.AnalyticFunction;
import functions.Function;
import functions.TabularFunction;
import calculus.Derivative_method;
import calculus.Numerical_derivative;
import utils.FileWork_2;
import utils.FunctionProcessor;
import java.util.ArrayList;

// Task 2
public class Main_2
{
    public static void main(String[] args)
    {
        double x_start = 1.5;
        double x_end = 6.5;
        double step = 0.05;
        double epsilon = 0.0001;

        System.out.println("\tЛаба 1 - Рефакторинг (Завдання 2)\n");

        // initialize components
        Derivative_method numericalDerivative = new Numerical_derivative(epsilon);
        FileWork_2 fileWork = new FileWork_2();
        FunctionProcessor processor = new FunctionProcessor(numericalDerivative, fileWork);

        // function 1
        System.out.println("Обчислення функції 1: f(x) = exp(-x^2) * sin(x)");
        Function function1 = new AnalyticFunction(x -> Math.exp(-x * x) * Math.sin(x));
        processor.compute_and_save("function1_2.txt", function1, x_start, x_end, step);

        System.out.printf("\n");

        // function 2 with different a
        double[] a_values = {0.5, 1.0, 1.5};
        for(double a: a_values)
        {
            System.out.println("Обчислення функції 2 з а = " + a + ": f(x) = exp(-" + a + "* x^2 * sin(x)");
            Function function2 = new AnalyticFunction(x -> Math.exp(-a * x * x) * Math.sin(x));
            processor.compute_and_save("function2_a" + a + "_2.txt", function2, x_start, x_end, step);
        }

        System.out.printf("\n");

        // function 3
        System.out.println("Обчислння функції 3: Таблична функція sin(x)");
        ArrayList<Double> x_table = new ArrayList<>();
        ArrayList<Double> y_table = new ArrayList<>();

        for (double x = x_start; x <= x_end; x += step)
        {
            x_table.add(x);
            y_table.add(Math.sin(x));
        }
        Function function3 = new TabularFunction(x_table, y_table);
        processor.compute_and_save("function3_tabular_2.txt", function3, x_start, x_end, step);

        System.out.println("фсе дубль два");
    }
}
