import functions.StringFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

// main application window for Task 3
public class MainWindow extends JFrame
{
    private JTextField functionInput;
    private JTextField paramAInput;
    private JTextField xMinInput;
    private JTextField xMaxInput;

    private JPanel chartContainer;

    public MainWindow() {
        super("Завдання 3: Аналізатор функцій");

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setLayout(new BorderLayout());

        // setup control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("f(x) ="));
        functionInput = new JTextField("exp(-a*x)*sin(x)", 12);
        controlPanel.add(functionInput);

        controlPanel.add(new JLabel("a ="));
        paramAInput = new JTextField("1.5", 4);
        controlPanel.add(paramAInput);

        controlPanel.add(new JLabel("X від:"));
        xMinInput = new JTextField("0.0", 4);
        controlPanel.add(xMinInput);

        controlPanel.add(new JLabel("до:"));
        xMaxInput = new JTextField("10.0", 4);
        controlPanel.add(xMaxInput);

        JButton buildButton = new JButton("Побудувати графік");
        controlPanel.add(buildButton);
        add(controlPanel, BorderLayout.NORTH);

        // setup chart area
        chartContainer = new JPanel();
        chartContainer.setLayout(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);

        // action listener for the button
        buildButton.addActionListener(e -> drawChart());
        drawChart(); // initial plot
    }

    private void drawChart()
    {
        try
        {
            // read user inputs
            String funcText = functionInput.getText();
            double a = Double.parseDouble(paramAInput.getText());
            double xMin = Double.parseDouble(xMinInput.getText());
            double xMax = Double.parseDouble(xMaxInput.getText());

            // create function from string
            StringFunction function = new StringFunction(funcText, a);

            // create series for the function and its derivative
            XYSeries seriesFunc = new XYSeries("Функція f(x)");
            XYSeries seriesDeriv = new XYSeries("Похідна f'(x)");

            // calculate 100 points between xMin and xMax
            int steps = 100;
            double stepSize = (xMax - xMin) / steps;

            for (int i = 0; i <= steps; i++)
            {
                double x = xMin + (i * stepSize);
                seriesFunc.add(x, function.calculate(x));
                seriesDeriv.add(x, function.calculateAnalyticalDerivative(x));
            }

            // combine series into a dataset
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(seriesFunc);
            dataset.addSeries(seriesDeriv);

            // configure JFreeChart plot
            String derivativeTitle = "f'(x) = " + function.getDerivativeFormula();
            JFreeChart chart = ChartFactory.createXYLineChart(
                    derivativeTitle, // chart title displays the derivative formula
                    "Вісь X",
                    "Вісь Y",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            // refresh the chart container
            chartContainer.removeAll();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartContainer.add(chartPanel, BorderLayout.CENTER);
            chartContainer.validate(); // re-render the window

        }
        catch (Exception ex)
        {
            // display error if formula or numbers are invalid
            JOptionPane.showMessageDialog(this,
                    "ошибочка вводу! перевір числа та формулу.\n" + ex.getMessage(),
                    "помилка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}