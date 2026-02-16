import calc_task.Calculator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simple Calculator");

        double a = 10;
        double b = 5;

        System.out.println(a + " + " + b + " = " + Calculator.add(a, b));
        System.out.println(a + " - " + b + " = " + Calculator.subtract(a, b));
        System.out.println(a + " * " + b + " = " + Calculator.multiply(a, b));
        System.out.println(a + " / " + b + " = " + Calculator.divide(a, b));
    }
}