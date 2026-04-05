// task 2, 3
package calculus;

import functions.Function;

// implementation of numerical differentiation using the central difference method
public class Numerical_derivative implements Derivative_method
{
    private final double epsilon;

    public Numerical_derivative(double epsilon)
    {
        this.epsilon = epsilon;
    }

    @Override
    public double calculate(Function function, double x)
    {
        double fx_plus = function.calculate(x + epsilon);
        double fx_minus = function.calculate(x - epsilon);
        return (fx_plus - fx_minus) / (2 * epsilon);
    }
}
