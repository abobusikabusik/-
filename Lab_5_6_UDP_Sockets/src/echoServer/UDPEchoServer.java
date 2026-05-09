package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer extends UDPServer
{
    public final static int DEFAULT_PORT = 7;

    public UDPEchoServer()
    {
        super(DEFAULT_PORT);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException
    {
        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
        socket.send(reply);
    }

    public static void main(String[] args)
    {
        UDPServer server = new UDPEchoServer();
        Thread t = new Thread(server);
        t.start();

        System.out.println("Start echo-server on port " + DEFAULT_PORT + "...");
        try
        {
            Thread.sleep(20000); // Сервер працюватиме 20 секунд
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        server.shutDown();
        System.out.println("Finish echo-server...");
    }
}