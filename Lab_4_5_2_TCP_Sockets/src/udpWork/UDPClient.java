package udpWork;
import java.io.*;
import java.net.*;

public class UDPClient
{
    private ActiveUsers userList = null;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private int serverPort = -1;
    private InetAddress serverAddress = null;

    public UDPClient(String address, int port)
    {
        userList = new ActiveUsers();
        serverPort = port;
        try
        {
            serverAddress = InetAddress.getByName(address);
            socket = new DatagramSocket();
            socket.setSoTimeout(5000); // timeout 5 sec
        }
        catch (UnknownHostException e)
        {
            System.out.println("Error: " + e);
        }
        catch (SocketException e)
        {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) throws ClassNotFoundException
    {
        byte[] buffer = new byte[bufferSize];
        try
        {
            // sending empty packet to sign in
            packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sending request...");

            while (true)
            {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // if empty packet - the end of list of users
                if (packet.getLength() == 0) break;

                ObjectInputStream in = new ObjectInputStream(
                        new ByteArrayInputStream(packet.getData(), 0, packet.getLength()));
                User usr = (User) in.readObject();
                userList.add(usr);
                clear(buffer);
            }
        }
        catch(SocketTimeoutException e)
        {
            System.out.println("Server is unreachable: " + e);
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
        finally
        {
            if (socket != null && !socket.isClosed())
            {
                socket.close();
            }
        }
        System.out.println("Registered users: " + userList.size());
        System.out.println(userList);
    }

    private void clear(byte[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = 0;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        (new UDPClient("127.0.0.1", 1501)).work(256);
    }
}