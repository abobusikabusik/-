// task 2, 3
package utils;

import java.io.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

// handles saving results, reading standard text files, and parsing CSV data into collections
public class FileWork_2
{
    // save results (x, f(x), f'(x)) to a tab-separated file
    public void save_to_file(String filename, List<Double> x_values, List<Double> f_values, List<Double> derivative_values)
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

    // reads x and y values from a space/tab separated text file
    public void read_from_file(String filename, List<Double> x_values, List<Double> y_values)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.trim().split("\\s+"); // cut spaces, tabulation and newline
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

    // read data from CSV and populate collections (TreeMap, TreeSet)
    public void read_csv_to_collections(String filename, TreeMap<Double, Double> treeMap, TreeSet<Point> treeSet)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.trim().split("[,;]"); // cut when theres ',' or ';'
                if (parts.length >= 2)
                {
                    try
                    {
                        double x = Double.parseDouble(parts[0].trim());
                        double y = Double.parseDouble(parts[1].trim());
                        treeMap.put(x, y);
                        treeSet.add(new Point(x, y));
                    }
                    catch (NumberFormatException e) // if we try to read x or y (text)
                    {
                        // ignoring
                    }
                }
            }
            System.out.println("дані зчитано з файліку CSV: " + filename);
            System.out.println("\nрозмір TreeMap: " + treeMap.size() + ", розмір TreeSet: " + treeSet.size());
        }
        catch (IOException e)
        {
            System.err.println("вийшла ошибочка у читанні CSV файлу: " + e.getMessage());
        }
    }
}