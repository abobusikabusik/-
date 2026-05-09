package bulletinBoardService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSenderReceiver
{
    private String name;
    private InetAddress addr;
    private int port = 3456;
    private MulticastSocket group;

    public MulticastSenderReceiver(String name)
    {
        this.name = name;
        try
        {
            addr = InetAddress.getByName("224.0.0.1");
            // setup multicast socket
            group = new MulticastSocket(port);
            new Receiver().start();
            new Sender().start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // thread for sending messages to the group
    private class Sender extends Thread
    {
        public void run()
        {
            try
            {
                BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
                while (true)
                {
                    // read from console and send to multicast ip
                    String msg = name + ": " + fromUser.readLine();
                    byte[] out = msg.getBytes();
                    DatagramPacket pkt = new DatagramPacket(out, out.length, addr, port);
                    group.send(pkt);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // thread for listening to the group
    private class Receiver extends Thread
    {
        public void run()
        {
            try
            {
                byte[] in = new byte[256];
                DatagramPacket pkt = new DatagramPacket(in, in.length);

                // join multicast group to receive messages
                group.joinGroup(addr);
                while (true)
                {
                    // block until message arrives, then print it
                    group.receive(pkt);
                    System.out.println(new String(pkt.getData(), 0, pkt.getLength()));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        // use default random name if no args provided
        String userName = args.length > 0 ? args[0] : "User_" + (int)(Math.random() * 100);
        System.out.println("Запуск чату для користувача: " + userName);
        new MulticastSenderReceiver(userName);
    }
}