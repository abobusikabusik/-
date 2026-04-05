// task 1, 2
package functions;

import java.util.function.Function;

// represents a function defined by a formula (lambda expression)
public class AnalyticFunction implements functions.Function
{
    private final Function<Double,Double> expression;

    public AnalyticFunction(Function<Double, Double> expression)
    {
        this.expression = expression;
    }

    @Override
    public double calculate(double x)
    {
        return expression.apply(x);
    }
}
