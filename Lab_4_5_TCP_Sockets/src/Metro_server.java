import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Metro_server
{
    private int port;
    private Map<String, Card> card_database = new HashMap<>(); //storage for cards

    public Metro_server(int port)
    {
        this.port = port;
    }

    public void start()
    {
        try (ServerSocket server_socket = new ServerSocket(port))
        {
            System.out.println("Metro Server started on port: " + port);

            while (true)
            {
                // waiting for client connection
                try (Socket client_socket = server_socket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(client_socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(client_socket.getInputStream()))
                {
                    // operation type
                    String command = (String) in.readObject();
                    process_command(command, in, out);

                }
                catch (Exception e)
                {
                    System.out.println("Client handler error: " + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private void process_command(String command, ObjectInputStream in, ObjectOutputStream out) throws Exception
    {
        switch (command)
        {
            case "REGISTER":
                Card new_card = (Card) in.readObject();
                if (card_database.containsKey(new_card.get_id()))
                {
                    out.writeObject("Error: Card with ID " + new_card.get_id() + " already exists. :(");
                }
                else
                {
                    card_database.put(new_card.get_id(), new_card);
                    out.writeObject("Success: Card [" + new_card.get_id() + "] registered successfully.");
                }
                break;

            case "GET_INFO":
                String id_info = (String) in.readObject();
                Card card = card_database.get(id_info);
                if (card != null)
                {
                    out.writeObject(card.toString());
                }
                else
                {
                    out.writeObject("Error: Card with ID " + id_info + " not found.");
                }
                break;

            case "DEPOSIT":
                String id_dep = (String) in.readObject();
                double amount = in.readDouble();
                double MAX_BALANCE = 100000.0; // The maximum allowed balance

                if (card_database.containsKey(id_dep))
                {
                    Card c = card_database.get(id_dep);
                    double potential_balance = c.get_balance() + amount;

                    if (potential_balance > MAX_BALANCE)
                    {
                        out.writeObject("Error: Deposit rejected. Max balance limit is " + MAX_BALANCE + " UAH.");
                    }
                    else
                    {
                        c.set_balance(potential_balance);
                        out.writeObject("Success: Yor new balance is " + c.get_balance());
                    }
                }
                else
                {
                    out.writeObject("Error: Card wasn't found. :(");
                }
                break;

            case "PAY":
                String id_pay = (String) in.readObject();
                double fare = 15.0;
                Card card_to_pay = card_database.get(id_pay);
                if (card_to_pay == null)
                {
                    // card ID doesn't exist in the database
                    out.writeObject("Error: Card wasn't found. Access denied.");
                }
                else if (card_to_pay.get_balance() < fare)
                {
                    // not enough money for the trip
                    out.writeObject("Error: Insufficient funds. Balance: " + card_to_pay.get_balance() + " UAH.");
                }
                else
                {
                    // deduct the fare and update the card
                    card_to_pay.set_balance(card_to_pay.get_balance() - fare);
                    out.writeObject("Success: Trip paid! Remaining balance: " + card_to_pay.get_balance() + " UAH.");
                    System.out.println("Payment processed for Card ID: " + id_pay);
                }
                break;

            default:
                out.writeObject("Error: Unknown command.");
        }
    }

    public static void main(String[] args)
    {
        new Metro_server(1234).start();
    }
}
