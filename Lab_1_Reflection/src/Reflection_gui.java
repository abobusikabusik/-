import javax.swing.*;
import java.awt.*;

public class Reflection_gui
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Reflection Analyzer");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField input = new JTextField();
        JButton button = new JButton("Аналізувати");

        JTextArea output = new JTextArea();
        output.setEditable(false);

        JScrollPane scroll = new JScrollPane(output);

        button.addActionListener(e ->
        {
            String class_name = input.getText();

            String result = Reflection_tasks.analyze_class(class_name);

            output.setText(result);
        });

        JPanel top = new JPanel(new BorderLayout());

        top.add(input, BorderLayout.CENTER);
        top.add(button, BorderLayout.EAST);

        frame.add(top, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}