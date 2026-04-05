// task 3
package functions;

import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;
import edu.hws.jcm.data.Expression;

// parses a function from a string, supports parameters and analytical derivatives
public class StringFunction implements Function
{
    private final Expression parsedExpression;
    private final Variable xVariable;
    private final Variable aVariable;
    private final Expression derivativeExpression;

    public StringFunction(String functionString)
    {
        this(functionString, 0.0);
    }

    public StringFunction(String functionString, double aValue)
    {
        Parser parser = new Parser();

        // register variables
        this.xVariable = new Variable("x");
        parser.add(xVariable);
        this.aVariable = new Variable("a");
        this.aVariable.setVal(aValue);
        parser.add(aVariable);

        // parse expression and compute analytical derivative
        this.parsedExpression = parser.parse(functionString);
        this.derivativeExpression = parsedExpression.derivative(xVariable);
    }

    @Override
    public double calculate(double x)
    {
        xVariable.setVal(x);
        return parsedExpression.getVal();
    }

    public double calculateAnalyticalDerivative(double x)
    {
        xVariable.setVal(x);
        return derivativeExpression.getVal();
    }

    public String getDerivativeFormula()
    {
        return derivativeExpression.toString();
    }
}