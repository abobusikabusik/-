// task 2, 3
package calculus;

import functions.Function;

// interface for different differentiation methods
public interface Derivative_method
{
    double calculate(Function function, double x);
}
