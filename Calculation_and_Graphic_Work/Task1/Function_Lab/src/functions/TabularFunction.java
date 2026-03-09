package functions;

import java.util.ArrayList;
import java.util.Collections;

public class TabularFunction implements Function
{
    private final ArrayList<Double> x_values;
    private final ArrayList<Double> y_values;

    public TabularFunction(ArrayList<Double> x_values, ArrayList<Double> y_values)
    {
        if (x_values.size() != y_values.size())
        {
            throw new IllegalArgumentException("Масивчики повинні мати однаковий розмір бро");
        }

        this.x_values = new ArrayList<>(x_values);
        this.y_values = new ArrayList<>(y_values);
    }

    public double calculate(double x)
    {
        int index = Collections.binarySearch(x_values, x);

        if (index >= 0)
        {
            return y_values.get(index);
        }

        int insert_p = -index - 1;

        if (insert_p == 0)
        {
            return y_values.get(0);
        }

        if (insert_p >= x_values.size())
        {
            return y_values.get(x_values.size() - 1);
        }

        double x1 = x_values.get(insert_p - 1);
        double x2 = x_values.get(insert_p);
        double y1 = y_values.get(insert_p - 1);
        double y2 = y_values.get(insert_p);

        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }
}
