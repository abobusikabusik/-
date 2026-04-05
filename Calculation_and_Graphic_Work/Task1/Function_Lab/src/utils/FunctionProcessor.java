package utils;

import functions.Function;
import calculus.Derivative_method;
import java.util.ArrayList;
import java.util.List;

public class FunctionProcessor
{
    private final Derivative_method derivativeMethod;
    private final FileWork_2 fileWork;

    public FunctionProcessor(Derivative_method derivativeMethod, FileWork_2 fileWork)
    {
        this.derivativeMethod = derivativeMethod;
        this.fileWork = fileWork;
    }

    public void compute_and_save(String filename, Function function, double x_start, double x_end, double step)
    {
        List<Double> x_values = new ArrayList<>();
        List<Double> f_values = new ArrayList<>();
        List<Double> derivative_values = new ArrayList<>();

        for (double x = x_start; x <= x_end; x += step)
    {
            x_values.add(x);
            f_values.add(function.calculate(x));
            derivative_values.add(derivativeMethod.calculate(function, x));
        }

        fileWork.save_to_file(filename, x_values, f_values, derivative_values);
    }
}
