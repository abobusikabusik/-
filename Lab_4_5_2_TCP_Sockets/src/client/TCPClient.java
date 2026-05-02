package client;
import interfaces.Result;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient
{
    public static void main(String[] args)
    {
        try (Socket client = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream()))
        {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть число для обчислення факторіалу: ");
            int num = scanner.nextInt();

            String classFile = "out/production/Lab_4_5_2_TCP_Sockets/client/JobOne.class";
            out.writeObject(classFile);

            File file = new File(classFile);
            byte[] b = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file))
            {
                fis.read(b);
            }
            out.writeObject(b);

            // sending task object
            JobOne aJob = new JobOne(num);
            out.writeObject(aJob);

            // getting class - file result
            String resClassFile = (String) in.readObject();
            byte[] resBytes = (byte[]) in.readObject();

            File resFile = new File(resClassFile);
            resFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(resFile))
            {
                fos.write(resBytes);
            }

            // getting and printing result
            Result r = (Result) in.readObject();
            System.out.println("result = " + r.output() + ", time taken = " + r.scoreTime() + "ns");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}