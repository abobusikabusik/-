import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Metro_client
{
    private String host;
    private int port;

    public Metro_client(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public boolean send_request(String command, Object data, Double extra_data)
    {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream()))
        {
            out.writeObject(command); // send command

            // send relevant data based on command
            if (data != null) out.writeObject(data);
            if (extra_data != null) out.writeDouble(extra_data);

            out.flush();

            // receive and print the response
            Object response = in.readObject();
            System.out.println("Server says: " + response);
            return true;

        }
        catch (Exception e)
        {
            System.out.println("Connection error: " + e.getMessage());
            System.err.println("Make sure the server is running and try again.");
            return false;
        }
    }

    public static void main(String[] args)
    {
        Metro_client client = new Metro_client("localhost", 1234);
        Scanner scanner = new Scanner(System.in);

        // registering a new card for the first time
        boolean is_connected = client.send_request("REGISTER", new Card("001", "Sofia", 50.0), null);

        if (!is_connected)
        {
            System.out.println("Exiting application due to connection failure.");
            System.exit(0);
        }

        // checking balance/info
        client.send_request("GET_INFO", "001", null);

        // paying for a trip
        client.send_request("PAY", "001", null);

        // adding money (20.0)
        client.send_request("DEPOSIT", "001", 20.0);

        // menu
        while (true)
        {
            System.out.println("\n\tWelcome to our metro system menu! How can i help you?: \n");
            System.out.println("1. register new card");
            System.out.println("2. check card info");
            System.out.println("3. deposit money");
            System.out.println("4. pay for trip (15.0 UAH)");
            System.out.println("5. exit");

            int choice = Input_validator.read_int(scanner, "Select an option: ", 1, 5);

            if (choice == 5) break;

            switch (choice)
            {
                case 1:

                    String id = Input_validator.read_string(scanner, "Enter Card ID: ");
                    String name = Input_validator.read_string(scanner, "Enter Owner Name: ");
                    double bal = Input_validator.read_double(scanner, "Initial balance: ", 0.0, 100000.0);
                    client.send_request("REGISTER", new Card(id, name, bal), null);
                    break;

                case 2:

                    String check_id = Input_validator.read_string(scanner, "Enter Card ID to check: ");
                    client.send_request("GET_INFO", check_id, null);
                    break;

                case 3:

                    String dep_id = Input_validator.read_string(scanner, "Enter Card ID for deposit: ");
                    double amount = Input_validator.read_double(scanner, "Amount to deposit: ", 1.0, 50000.0);
                    client.send_request("DEPOSIT", dep_id, amount);
                    break;

                case 4:

                    String pay_id = Input_validator.read_string(scanner, "Enter Card ID to pay: ");
                    client.send_request("PAY", pay_id, null);
                    break;
            }
        }

        System.out.println("Session closed. See you next time!");
    }
}
