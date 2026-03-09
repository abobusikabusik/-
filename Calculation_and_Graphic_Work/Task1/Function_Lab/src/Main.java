import functions.AnalyticFunction;
import functions.Function;
import functions.TabularFunction;
import calculus.Derivative;
import utils.FileWork;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        double x_start = 1.5;
        double x_end = 6.5;
        double step = 0.05;
        double epsilon = 0.0001;

        System.out.println("\tЛаба 1");

        // function 1
        System.out.println("Обчислення функції 1: f(x) = exp(-x^2) * sin(x)");
        Function function1 = new AnalyticFunction(x -> Math.exp(-x * x) * Math.sin(x));
        compute_and_save("function1.txt", function1, x_start, x_end, step, epsilon);

        // function 2 with different a
        double[] a_values = {0.5, 1.0, 1.5};
        for(double a: a_values)
        {
            System.out.println("Обчислення функції 2 з а = " + a + ": f(x) = exp(-" + a + "* x^2 * sin(x)");
            Function function2 = new AnalyticFunction(x -> Math.exp(-a * x * x) * Math.sin(x));
            compute_and_save("function2_a" + a + ".txt", function2, x_start, x_end, step, epsilon);
        }

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
        compute_and_save("function3_tabular.txt", function3, x_start, x_end, step, epsilon);

        System.out.println("фсе");
    }

    private static void compute_and_save(String filename, Function function, double x_start, double x_end, double step, double epsilon)
    {
        ArrayList<Double> x_values = new ArrayList<>();
        ArrayList<Double> f_values = new ArrayList<>();
        ArrayList<Double> derivative_values = new ArrayList<>();

        for (double x = x_start; x <= x_end; x += step)
        {
            x_values.add(x);
            f_values.add(function.calculate(x));
            derivative_values.add(Derivative.calculate(function, x, epsilon));
        }

        FileWork.save_to_file(filename, x_values, f_values, derivative_values);
    }
}