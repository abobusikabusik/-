package my_beans;

public class Data
{
    private String date;
    private double x;
    private double y;

    public Data()
    {
        this.date = "";
        this.x = 0.0;
        this.y = 0.0;
    }

    public Data(String date, double x, double y)
    {
        this.date = date;
        this.x = x;
        this.y = y;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
}