// task 1, 2
package functions;

import java.util.ArrayList;
import java.util.Collections;

// represents a function defined by a table of (x, y) points
public class TabularFunction implements Function
{
    private final ArrayList<Double> x_values;
    private final ArrayList<Double> y_values;

    public TabularFunction(ArrayList<Double> x_values, ArrayList<Double> y_values)
    {
        if (x_values.size() != y_values.size())
        {
            throw new IllegalArgumentException("масивчики повинні мати однаковий розмір бро");
        }

        this.x_values = new ArrayList<>(x_values);
        this.y_values = new ArrayList<>(y_values);
    }

    public double calculate(double x)
    {
        // if x is below the first point - return the first y
        if (x <= x_values.get(0)) return y_values.get(0);

        // if x is above the last point - return the last y
        if (x >= x_values.get(x_values.size() - 1)) return y_values.get(y_values.size() - 1);

        // iterate through points to find the segment containing x
        for (int i = 0; i < x_values.size() - 1; i++)
        {
            double x1 = x_values.get(i);
            double x2 = x_values.get(i + 1);

            if (x >= x1 && x <= x2)
            {
                double y1 = y_values.get(i);
                double y2 = y_values.get(i + 1);

                // linear interpolation formula (straight line between points)
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }
        return 0;
    }
}
