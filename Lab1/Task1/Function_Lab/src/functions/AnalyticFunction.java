package functions;

public class AnalyticFunction implements Function
{
    private final java.util.function.Function<Double,Double> expression;

    public AnalyticFunction(java.util.function.Function<Double, Double> expression)
    {
        this.expression = expression;
    }

    public double calculate(double x)
    {
        return expression.apply(x);
    }
}
