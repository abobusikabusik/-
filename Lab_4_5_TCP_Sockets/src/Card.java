import java.io.Serializable;

public class Card implements Serializable
{
    private String id;
    private String owner_name;
    private double balance;

    public Card(String id, String owner_name, double balance)
    {
        this.id = id;
        this.owner_name = owner_name;
        this.balance = balance;
    }

    public String get_id() { return id; }
    public double get_balance() { return balance; }
    public String get_owner_name() { return owner_name; }
    public void set_balance(double balance) { this.balance = balance; }

    @Override
    public String toString()
    {
        return "Card #" + id + " [" + owner_name + "], balance: " + balance + " UAH.";
    }
}
