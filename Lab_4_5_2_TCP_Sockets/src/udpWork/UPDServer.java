package udpWork;
import java.io.*;
import java.net.*;

public class UPDServer
{
    private ActiveUsers userList = null;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private InetAddress address = null;
    private int port = -1;

    public UPDServer(int serverPort)
    {
        try
        {
            socket = new DatagramSocket(serverPort);
        }
        catch(SocketException e)
        {
            System.out.println("Error: " + e);
        }
        userList = new ActiveUsers();
    }

    public void work(int bufferSize)
    {
        try
        {
            System.out.println("Server start...");
            while (true)
            {
                getUserData(bufferSize);
                log(address, port);
                sendUserData();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
        finally
        {
            System.out.println("Server end...");
            if (socket != null && !socket.isClosed())
            {
                socket.close();
            }
        }
    }

    private void log(InetAddress address, int port)
    {
        System.out.println("Request from: " + address.getHostAddress() + " port: " + port);
    }

    private void clear(byte[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = 0;
        }
    }

    private void getUserData(int bufferSize) throws IOException
    {
        byte[] buffer = new byte[bufferSize]; // data from client
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet); // waiting for packet
        address = packet.getAddress();
        port = packet.getPort();
        User usr = new User(address, port);

        if (userList.isEmpty())
        {
            userList.add(usr);
        }
        else if (!userList.contains(usr))
        {
            userList.add(usr);
        }
        clear(buffer);
    }

    private void sendUserData() throws IOException
    {
        byte[] buffer;
        for (int i = 0; i < userList.size(); i++)
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(userList.get(i));
            buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
        buffer = new byte[0]; // empty packet - all users are sent
        packet = new DatagramPacket(buffer, 0, address, port);
        socket.send(packet);
    }

    public static void main(String[] args)
    {
        (new UPDServer(1501)).work(256);
    }
}