package server;
import interfaces.Executable;
import java.io.*;
import java.net.*;

public class TCPServer
{
    public static void main(String[] args)
    {
        try (ServerSocket serverSocket = new ServerSocket(12345))
        {
            System.out.println("Сервер очікує на завдання...");

            while (true)
            {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream()))
                {

                    // getting name and class - file task from client
                    String classFile = (String) in.readObject();
                    classFile = classFile.replaceFirst("client", "server");
                    byte[] b = (byte[]) in.readObject();

                    // saving class - file
                    File file = new File(classFile);
                    file.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(file))
                    {
                        fos.write(b);
                    }

                    // getting object - task
                    Executable ex = (Executable) in.readObject();

                    // task with time measurement
                    double startTime = System.nanoTime();
                    Object output = ex.execute();
                    double endTime = System.nanoTime();
                    double completionTime = endTime - startTime;

                    // making result
                    ResultImpl r = new ResultImpl(output, completionTime);

                    // sending class - file result
                    String resultClassFile = "out/production/Lab_4_5_2_TCP_Sockets/server/ResultImpl.class";
                    out.writeObject(resultClassFile.replaceFirst("server", "client"));

                    File resFile = new File(resultClassFile);
                    byte[] bo = new byte[(int) resFile.length()];
                    try (FileInputStream fis = new FileInputStream(resFile))
                    {
                        fis.read(bo);
                    }
                    out.writeObject(bo);

                    // sending object - file result
                    out.writeObject(r);

                }
                catch (Exception e)
                {
                    System.err.println("Помилка обробки клієнта: " + e);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}