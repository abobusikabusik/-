package calculus;

import functions.Function;

public class Derivative
{
    public static double calculate(Function function, double x, double epsilon)
    {
        double fx_plus = function.calculate(x + epsilon);
        double fx_minus = function.calculate(x - epsilon);

        return (fx_plus - fx_minus) / (2 * epsilon);
    }
}
