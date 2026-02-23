package utils;

import java.io.*;
import java.util.ArrayList;

public class FileWork
{
    public static void save_to_file(String filename, ArrayList<Double> x_values, ArrayList<Double> f_values, ArrayList<Double> derivative_values)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename)))
        {
            writer.println("x\tf(x)\tf'(x)");
            for (int i = 0; i < x_values.size(); i++)
            {
                writer.printf("%.4f\t%.6f\t%.6f%n", x_values.get(i), f_values.get(i), derivative_values.get(i));
            }
            System.out.println("зберегли дані у файлік: " + filename);
        }
        catch (IOException e)
        {
            System.err.println("вийшла ошибочка запису у файл: " + e.getMessage());
        }
    }

    public static void read_from_file(String filename, ArrayList<Double> x_values, ArrayList<Double> y_values)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2)
                {
                    x_values.add(Double.parseDouble(parts[0]));
                    y_values.add(Double.parseDouble(parts[1]));
                }
            }
            System.out.println("дані зчитано з файліку: " + filename);
        }
        catch (IOException e)
        {
            System.err.println("вийшла ошибочка у читанні файлу: " + e.getMessage());
        }
    }
}
